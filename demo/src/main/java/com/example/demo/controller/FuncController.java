package com.example.demo.controller;

import com.example.demo.Function;
import com.example.demo.Rule;
import com.example.demo.entity.FuncEntity;
import com.example.demo.entity.Node;
import com.example.demo.entity.Plan;
import com.example.demo.entity.TaskSystem;
import com.example.demo.service.FuncService;
import com.example.demo.service.ParaService;
import com.example.demo.service.RuleService;
import com.example.demo.utils.Msg;
//import javafx.concurrent.Task;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@RestController
public class FuncController {
    @Autowired
    private FuncService funcService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private ParaService paraService;

    @Autowired
    Map<String, Function.FunctionInterface> funcMap;
    @Autowired
    Map<String, Rule.RuleInterface> ruleMap;
    @PersistenceContext
    private EntityManager entityManager;



    @RequestMapping("/CalPara")
    public Msg Calculate(@RequestBody JSONObject params) {
        Plan plan = com.alibaba.fastjson.JSONObject.parseObject(params.toString(), Plan.class);
        Object listObj = params.get("update");
        List<String> updateList = com.alibaba.fastjson.JSONObject.parseArray(listObj.toString(), String.class);

        for (String para : updateList){
            List<Integer> list = ruleService.getRule(para);
            System.out.println("new rule parameter: " + para);
            System.out.println(list);
            // 此处有规则重复验证问题，待解决
            for (int i = 0; i < list.size(); i++){
                if (list.get(i) == 1){
                    String str = "rule_" + (i + 1);
                    System.out.println("verify " + str);
                    if (!ruleMap.get(str).verify(plan)){
                        Msg msg = new Msg(-1, plan, Rule.para_of_rule.get(2-i));
                        return msg;
                    }
                }
            }
        }

        for (String para : updateList){
            List<Integer> list = funcService.getFunc(para);
            System.out.println("new func parameter: " + para);
            System.out.println(list);
            // 此处有函数重复验证问题，待解决
            for (int i = 0; i < list.size(); i++){
                if (list.get(i) == 1){
                    String str = "function_" + (i + 1);
                    System.out.println("call " + str);
                    funcMap.get(str).func(plan);
                }
            }

        }
        return new Msg(0, plan);
    }

    @RequestMapping("dataForTopo")
    public Msg dataForTopo(@RequestBody JSONObject params){
        Plan plan = com.alibaba.fastjson.JSONObject.parseObject(params.toString(), Plan.class);
        Object listObj = params.get("update");
        List<String> updateList = com.alibaba.fastjson.JSONObject.parseArray(listObj.toString(), String.class);
//        FuncEntity f = paraService.getPara(1);
//        System.out.println(f.getFuncId());
//        if (f.getPara_1() != null){
//            System.out.println(f.getPara_1());
//        }
//        if (f.getPara_2() != null){
//            System.out.println(f.getPara_2());
//        }
//        if (f.getPara_3() != null){
//            System.out.println(f.getPara_3());
//        }
//        if (f.getPara_4() != null){
//            System.out.println(f.getPara_4());
//        }
//        if (f.getPara_5() != null){
//            System.out.println(f.getPara_5());
//        }

        Set<Integer> funcs = new HashSet<>();
        Map<String, Node> paraToNode = new HashMap<>();
        Map<String, Integer> paraToFuncId = new HashMap<>();

        // 遍历更新的参数，获取所涉及函数的id，放入set中去重
        for (String para : updateList){
            List<Integer> list = funcService.getFunc(para);
            System.out.println("new func parameter: " + para);
            for (int i = 0; i < list.size(); i++){
                if (list.get(i) == 1){
                    Integer func_id = i + 1;
                    funcs.add(func_id);
                }
            }
        }

        // 遍历函数，从数据库读取该函数的所有参数
        // 对每一个参数，如有需要新建对应节点，放入参数名-结点的map
        // 建图
        for (Integer func_id : funcs){
            FuncEntity func = paraService.getPara(func_id);
            List<String> paraList = func.getParaList();
            String resultPara = func.getResultPara();

            if (!paraToNode.containsKey(resultPara)){
                Node node = new Node(resultPara);
                paraToNode.put(resultPara, node);
            }
            Node resultNode = paraToNode.get(resultPara);
            resultNode.isResult = true;
            paraToFuncId.put(resultNode.name, func_id);
//            System.out.println("-----func_" + func_id + "------");
            for (String para : paraList){
                if (para != null){
//                    System.out.println(para);
                    if (!paraToNode.containsKey(para)){
                        Node node = new Node(para);
                        paraToNode.put(para, node);
                    }
                    Node paraNode = paraToNode.get(para);
                    // map中存的node对象是引用，paraNode修改时，map的映射对象也会随之修改，不需要put回去
                    paraNode.inDegree++;
                    resultNode.nextList.add(paraNode);
                }
            }
        }

        // 所有参数对应的结点
        List<Node> nodes = new ArrayList<>(paraToNode.values());
//        System.out.println(nodes.size());
        // 排序
        List<String> result = topoSort(nodes);
        System.out.println(result);

        // 参数有环
        if (result.get(0) == "ring"){
            result.remove(0);
            return new Msg(-3, plan, result);

        }
        // 排序结果与函数执行顺序相反
        Collections.reverse(result);

        for (String para : result){
            Integer func_id = paraToFuncId.get(para);
            String str = "function_" + (func_id);
            funcMap.get(str).func(plan);
        }

        return new Msg(0, plan);
    }

    // 中期展示测试数据
    @RequestMapping("Calshow")
    public JSONObject Cal(@RequestBody JSONObject params){
        JSONObject res = new JSONObject();
        res.put("msg", 0);

        List<String> errList = new ArrayList<>();
        res.put("errorPara", errList);

        params.discard("update");
        JSONObject di_1 = JSONObject.fromObject(params.get("dimension_1"));
        JSONObject di_2 = JSONObject.fromObject(params.get("dimension_2"));
        JSONObject di_3 = JSONObject.fromObject(params.get("dimension_3"));
        Double para_E = (Double)di_1.get("para_A") + (Double)di_1.get("para_B") - (Double)di_2.get("para_C") - (Double)di_2.get("para_D");
        di_3.put("para_E", para_E);
        params.put("dimension_3", di_3);

        res.put("plan", params);
        return res;
    }


    @RequestMapping("/ruleTest")
    public void ruleTest(@RequestBody JSONObject params){
        Object updateObj = params.get("update");
        Object dataObj = params.get("data");
        Map<String, Double> dataJson = JSONObject.fromObject(dataObj);
        Map<String, Double> updateJson = JSONObject.fromObject(updateObj);
        Map<String, Double> data = new HashMap<>();
        Map<String, Double> update = new HashMap<>();
        data.putAll(dataJson);
        update.putAll(updateJson);


//        ruleMap.get("rule_1").verify(data);
        return;
    }


    // 本地方法测试
    @RequestMapping("nativeTest")
    public void nativeTest(@RequestBody JSONObject params){
        Plan plan = com.alibaba.fastjson.JSONObject.parseObject(params.toString(), Plan.class);
        Object listObj = params.get("update");
        List<String> updateList = com.alibaba.fastjson.JSONObject.parseArray(listObj.toString(), String.class);

        ruleMap.get("rule_test").verify(plan);

    }

    // json转实体类测试
    @RequestMapping("jsonTest")
    public void jsonTest(@RequestBody JSONObject params){
        Plan plan = com.alibaba.fastjson.JSONObject.parseObject(params.toString(), Plan.class);
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(plan));
    }

    // 拓扑排序测试
    @RequestMapping("topoTest")
    public void topoTest(){
        List<Node> nodes_1 = new ArrayList<>();
        Node nodeA = new Node("A", true, 1);
        Node nodeB = new Node("B", true, 1);
        Node nodeC = new Node("C", false, 1);
        Node nodeD = new Node("D", false, 1);
        Node nodeE = new Node("E", false, 1);
        Node nodeF = new Node("F", false, 1);
        Node nodeG = new Node("G", true, 0);
        nodeA.nextList.add(nodeB);
        nodeB.nextList.add(nodeC);
        nodeC.nextList.add(nodeD);
        nodeD.nextList.add(nodeA);
        nodes_1.add(nodeA);
        nodes_1.add(nodeB);
        nodes_1.add(nodeC);
        nodes_1.add(nodeD);
        List<String> result =topoSort(nodes_1);
        System.out.println(result.get(0));

        List<Node> nodes_2 = new ArrayList<>();
        nodeA.nextList.clear();
        nodeB.nextList.clear();
        nodeC.nextList.clear();
        nodeD.nextList.clear();
        nodeA.nextList.add(nodeB);
        nodeA.nextList.add(nodeC);
        nodeA.nextList.add(nodeD);
        nodeB.nextList.add(nodeE);
        nodeB.nextList.add(nodeF);
        nodeG.nextList.add(nodeA);

        nodes_2.add(nodeA);
        nodes_2.add(nodeB);
        nodes_2.add(nodeC);
        nodes_2.add(nodeD);
        nodes_2.add(nodeE);
        nodes_2.add(nodeF);
        nodes_2.add(nodeG);
        result =topoSort(nodes_2);
        System.out.println(result);

    }

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


    @RequestMapping("getAllFuncs")
    public void getAllFunc(){

//        funcService.getAllFunc();
        Node node1 = new Node("111");
        Node node2 = new Node("222");
        Node node3 = new Node("333");
        List<Node> list1 = new ArrayList<>();
        List<Node> list2 = new ArrayList<>();
        list1.add(node1);
        list1.add(node2);
        list1.add(node3);

        List<Node> list3 = new ArrayList<>();
        list3.addAll(list1);

        for (Node node : list3){
            if (node.name == "111"){
                list2.add(node);
            }
        }
        Node n = list2.get(0);
        n.name = "666";

        for (Node node : list1){
            System.out.println(node.name);
        }

//        ArrayList<String> list5 = new ArrayList<>();
//        ArrayList<String> list6 = new ArrayList<>();
//        list5.add("aaa");
//        list5.add("bbb");
//        list5.add("ccc");
//        list6.addAll(list5);
//        System.out.println(list6);

//        Set<String> q = new HashSet<>();
//        q.add("aaaa");
//        q.add("bbbb");
//        System.out.println(q);
//        q.add("cccc");
//        System.out.println(q);

        Map<TaskSystem, String> m = new HashMap<>();
        TaskSystem t = new TaskSystem();
        t.setTaskCost(100.0);
        t.setTaskWeight(200.0);
        String s = "test";
        m.put(t, s);
        System.out.println(m.get(t));
        m.remove(t);
        t.setTaskCost(50.0);
        m.put(t, s);
        System.out.println(m.get(t));

        TaskSystem tt = new TaskSystem();
        tt.setTaskCost(300.0);
        tt.setTaskWeight(400.0);
        JSONObject js = JSONObject.fromObject(t);
        JSONObject js2 = new JSONObject();
        js2.put("aaa", 2);
        js2.put("bbb", 3);
        JSONObject j = new JSONObject();
        j.putAll(js);
        j.putAll(js2);
        System.out.println(j);



    }




}
