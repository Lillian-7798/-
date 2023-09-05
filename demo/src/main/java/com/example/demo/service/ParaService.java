package com.example.demo.service;

import com.example.demo.Function;
import com.example.demo.Rule;
import com.example.demo.dao.ParaDao;
import com.example.demo.entity.*;
import com.example.demo.repository.FuncRepository;
import com.example.demo.utils.Msg;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ParaService {
    @Autowired
    private ParaDao paraDao;

    @Autowired
    private FuncService funcService;
    @Autowired
    private RuleService ruleService;

    @Autowired
    Map<String, Function.FunctionInterface> funcMap;
    @Autowired
    Map<String, Rule.RuleInterface> ruleMap;

    // 所有参数名构成的数组
    private static String[] allPara = {
            "flightAltitude",
            "flightSpeed",
            "flightRange",
            "flightEndurance",

            "totalWeight",
            "totalCost",

            "length",
            "wing",
            "AR",

            "materialType",
            "bodyWeight",
            "bodyCost",

            "energyEndurance",
            "energyAbility",
            "energyWeight",
            "energyCost",

            "propulsionAbility",
            "propulsionWeight",
            "propulsionCost",

            "taskWeight",
            "taskCost",

            "groundCost"
    };



    // 参数名到参数对应结点的映射
    private Map<String, Node> paraNameToNode = new HashMap<>();
    // 作为结果的参数名到函数id的映射
    private Map<String, Integer> resultParaToFuncId = new HashMap<>();

    private Graph graph = new Graph();
    // 初始化，构造图
    public void graphInitialize(){
        for (String para : allPara){
            Node node = new Node(para);
            paraNameToNode.put(para, node);
        }

        List<Object[]> allFunc = funcService.getAllFunc();
        for (Object[] row : allFunc){
            Integer funcId = (Integer)row[0];
            // 作为结果的参数
            String resultPara = "";
            // 用于计算的参数列表
            List<String> paraList = new ArrayList<>();
            // 注意allPara对应参数的下标比此处少1，因为查询结果的第一列是函数id
            for (int i = 1; i < row.length; i++){
                if ((Integer)row[i] == 1){
                    paraList.add(allPara[i - 1]);
                }
                else if ((Integer)row[i] == 2){
                    resultPara = allPara[i - 1];
                }
            }
            // map中存的node对象是引用，resultNode修改时，map的映射对象也会随之修改，不需要put回去
            Node resultNode = paraNameToNode.get(resultPara);
            resultNode.isResult = true;
            resultParaToFuncId.put(resultPara, funcId);

            for (String para : paraList){
                Node paraNode = paraNameToNode.get(para);
                paraNode.inDegree++;
                resultNode.nextList.add(paraNode);
                paraNode.resultList.add(resultNode);
            }
        }
        graph.setNodes(new ArrayList<>(paraNameToNode.values()));
    }

    public FuncEntity getPara(Integer func_id){
        return paraDao.getFunc(func_id);
    }

    public Msg CalculatePara(TransferData data){
        Plan plan = data.getPlan();
        Plan oldPlan = new Plan(plan);
        List<String> updateList = data.getUpdate();

        // 本次查询的子图包含的结点
        List<Node> subGraphNodes = new ArrayList<>();

        // 将updatelist中参数对应的结点放入子图
        Set<Node> paraQueue = new HashSet<>();
        for (String s : updateList){
            paraQueue.add(paraNameToNode.get(s));
        }

        // 寻找闭包，构造子图
        int lastLength;
        do {
            lastLength = paraQueue.size();
            for (Node curNode : paraQueue){
                if (!subGraphNodes.contains(curNode)){
                    subGraphNodes.add(curNode);
                }
                paraQueue.addAll(curNode.resultList);
                if (paraQueue.size() != lastLength){
                    break;
                }
            }
        }while(lastLength != paraQueue.size());
        for (Node node : subGraphNodes){
            System.out.println(node.name);
        }

        // 排序
        List<String> result = topoSort(subGraphNodes);
        System.out.println(result);

        // 参数有环
        if (result.get(0) == "ring"){
            result.remove(0);
            return new Msg(-3, plan, result);

        }
        // 排序结果与函数执行顺序相反
        Collections.reverse(result);

        // 调用函数
        for (String para : result){
            Integer func_id = resultParaToFuncId.get(para);
            String str = "function_" + (func_id);
            funcMap.get(str).func(plan);
        }

        // 比较新旧方案，寻找有变动的参数
        List<String> changeList = new ArrayList<>();
        JSONObject jsOldPlan = oldPlan.toJsonObj();
        JSONObject jsPlan = plan.toJsonObj();
        System.out.println(jsOldPlan);
        Set<String> ks = jsPlan.keySet();
        for (String key : ks){
            if (key.equals("materialType")){
                if (jsPlan.getString(key).compareTo(jsOldPlan.getString(key)) != 0){
                    changeList.add(key);
                }
            }
            else if (Double.compare(jsPlan.getDouble(key), jsOldPlan.getDouble(key)) != 0){
                changeList.add(key);
            }
        }


        return new Msg(0, changeList, plan);
    }

//    public Msg CalculatePara(TransferData data){
//        Plan plan = data.getPlan();
//        List<String> updateList = data.getUpdate();
//
//
//        Set<String> rules = new HashSet<>();
//        for (String para : updateList){
//            List<Integer> list = ruleService.getRule(para);
//            System.out.println("new rule parameter: " + para);
//            System.out.println(list);
//            // 此处有规则重复验证问题，待解决
//            for (int i = 0; i < list.size(); i++){
//                if (list.get(i) == 1){
//                    String ruleName = "rule_" + (i + 1);
//                    System.out.println("verify " + ruleName);
//                    rules.add(ruleName);
//                }
//            }
//            for (String ruleName : rules){
//                if (!ruleMap.get(ruleName).verify(plan)){
//                    Msg msg = new Msg(-1, plan, Rule.para_of_rule.get(ruleName));
//                    return msg;
//                }
//            }
//        }
//
//
//
//
//
//
//        Set<Integer> funcs = new HashSet<>();
//        Map<String, Node> paraToNode = new HashMap<>();
//        Map<String, Integer> paraToFuncId = new HashMap<>();
//
//        // 遍历更新的参数，获取所涉及函数的id，放入set中去重
//        for (String para : updateList){
//            List<Integer> list = funcService.getFunc(para);
//            System.out.println("new func parameter: " + para);
//            for (int i = 0; i < list.size(); i++){
//                if (list.get(i) == 1){
//                    Integer func_id = i + 1;
//                    funcs.add(func_id);
//                }
//            }
//        }
//
//        // 遍历函数，从数据库读取该函数的所有参数
//        // 对每一个参数，如有需要新建对应节点，放入参数名-结点的map
//        // 建图
//        for (Integer func_id : funcs){
//            FuncEntity func = getPara(func_id);
//            List<String> paraList = func.getParaList();
//            String resultPara = func.getResultPara();
//
//            if (!paraToNode.containsKey(resultPara)){
//                Node node = new Node(resultPara);
//                paraToNode.put(resultPara, node);
//            }
//            Node resultNode = paraToNode.get(resultPara);
//            resultNode.isResult = true;
//            paraToFuncId.put(resultNode.name, func_id);
////            System.out.println("-----func_" + func_id + "------");
//            for (String para : paraList){
//                if (para != null){
////                    System.out.println(para);
//                    if (!paraToNode.containsKey(para)){
//                        Node node = new Node(para);
//                        paraToNode.put(para, node);
//                    }
//                    Node paraNode = paraToNode.get(para);
//                    // map中存的node对象是引用，paraNode修改时，map的映射对象也会随之修改，不需要put回去
//                    paraNode.inDegree++;
//                    resultNode.nextList.add(paraNode);
//                }
//            }
//        }
//
//        // 所有参数对应的结点
//        List<Node> nodes = new ArrayList<>(paraToNode.values());
////        System.out.println(nodes.size());
//        // 排序
//        List<String> result = topoSort(nodes);
//        System.out.println(result);
//
//        // 参数有环
//        if (result.get(0) == "ring"){
//            result.remove(0);
//            return new Msg(-3, plan, result);
//
//        }
//        // 排序结果与函数执行顺序相反
//        Collections.reverse(result);
//
//        for (String para : result){
//            Integer func_id = paraToFuncId.get(para);
//            String str = "function_" + (func_id);
//            funcMap.get(str).func(plan);
//        }
//
//        return new Msg(0, plan);
//    }


    // 拓扑排序
    public List<String> topoSort(List<Node> nodes){
        if (nodes == null){
            return null;
        }
        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInDegree = new LinkedList<>();
        List<Node> sortResult = new ArrayList<>();
        List<Node> data = new ArrayList<>();
        data.addAll(nodes);
        for (Node node: nodes){
            inMap.put(node, node.inDegree);
            if (node.inDegree == 0){
                zeroInDegree.add(node);
                data.remove(node);
            }
        }

        while (!zeroInDegree.isEmpty()){
            Node current = zeroInDegree.poll();
            sortResult.add(current);
            for (Node next : current.nextList){
                if (!inMap.containsKey(next)){
                    continue;
                }
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0){
                    zeroInDegree.add(next);
                    data.remove(next);
                }
            }
        }

        List<String> result = new ArrayList<>();
        if (!data.isEmpty()) {
            result.add(0, "ring");
            for (Node node : data){
                result.add(node.name);
            }
            return result;
        }

        for (Node node : sortResult){
            if (node.isResult){
                result.add(node.name);
            }
        }
        return result;
    }
}
