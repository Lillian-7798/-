import logo from './logo.svg';
import './App.css';
import { ApolloClient, InMemoryCache, ApolloProvider } from '@apollo/client';
import { useQuery, gql } from '@apollo/client';
import { isNullableType } from 'graphql';
import { useState } from 'react';
import Model from './Model';

const client_1 = new ApolloClient({
  uri: 'http://localhost:9090/graphql', 
  cache: new InMemoryCache(),
});

const client_2 = new ApolloClient({
  uri: 'http://localhost:8080/graphql', 
  cache: new InMemoryCache(),
});
function SidebarLeft() {
  const alertPlaceholder = document.getElementById('liveAlertPlaceholder')
  const alert = (message, type) => {
    const wrapper = document.createElement('div')
    wrapper.innerHTML = [
      `<div class="alert alert-${type} alert-dismissible" role="alert">`,
      `   <div>${message}</div>`,
      '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
      '</div>'
    ].join('')

    alertPlaceholder.append(wrapper)
  }

    const [state, setState] = useState({
        loading_capacity:null,
        airframe_structure:{
            layout:null,
            strength_and_stiffness:null,
            materials:null,
            manufacturing_techniques:null
        },
        mission_capacity:{
            type:null,
            height:null,
            coverage:null
        },
        aerodynamic_configuration:{
            single_fuselage:null
        },
        propulsion_system:{
            electric_motor:null,
            propeller:null
        },
        cost:null,
        weight:null,
        mission_system:{
            communication:null,
            reconnaissance:null
        },
        ground_support:{
            station:null,
            land:null,
            transportation_and_storage:null,
            inspection_and_maintenance:null,
        },
        energy_system:{
            photovoltaic_system:null,
            energy_storage_battery:null
        },
        flight_management_system:{
            flight_control_and_actuation:null,
            integrated_management_of_energy_propulsion_and_missions:null
        },
        endurance:null,
        flight_capacity:{
            take_off_and_landing_requirements:null,
            height:null,
            speed:null,
            flight_range:null,
            endurance:null,
            flight_service_life:null
        }
    })
  const GetPlan=()=>{
    const load=document.getElementById("load").value
    const require = document.getElementById("require").value
    const cost = document.getElementById("cost").value
    console.log("获取对象",load,require,cost)
    const jsonObject={
      loading_capacity:parseFloat(load),
      endurance:parseFloat(require),
      cost:parseFloat(cost)
    }
    const jsonString = JSON.stringify(jsonObject);
    console.log("jsonString:"+jsonString)
    const query_1 = gql`
    query FindItem($jsonString: String!) {
      find(json:$jsonString){
    attributes{
      key,
      val{
        attrs{
          name,
          strVal,
          numVal,
          unit
        }
      }
    }
      }
    }
    `
    client_1.query({
      query:query_1,
      variables: {
        jsonString: jsonString
      }
    }).then(result=>{
      // console.log(result)
      var att=result.data.find[0].attributes
      console.log(att)
      setState(prevState => ({
        ...prevState,
        loading_capacity: att[0].val.attrs[0].numVal+' '+att[0].val.attrs[0].unit,
        airframe_structure:{
            layout:att[1].val.attrs[0].strVal,
            strength_and_stiffness:att[1].val.attrs[1].strVal,
            materials:att[1].val.attrs[2].strVal,
            manufacturing_techniques:att[1].val.attrs[3].strVal,
            weight:att[1].val.attrs[4].numVal+' '+att[1].val.attrs[4].unit,
            cost:att[1].val.attrs[5].unit+' '+att[1].val.attrs[5].numVal
        },
        mission_capacity:{
            type:att[2].val.attrs[0].strVal,
            height:att[2].val.attrs[1].strVal,
            coverage:att[2].val.attrs[2].numVal+' '+att[2].val.attrs[2].unit
        },
        aerodynamic_configuration:{
            single_fuselage:att[3].val.attrs[0].strVal,
            length:att[3].val.attrs[1].numVal+' '+att[3].val.attrs[1].unit,
            wing:att[3].val.attrs[2].numVal+' '+att[3].val.attrs[2].unit,
            AR:att[3].val.attrs[3].numVal+' '+att[3].val.attrs[3].unit
        },
        propulsion_system:{
            electric_motor:att[4].val.attrs[0].strVal,
            propeller:att[4].val.attrs[1].strVal,
            power:att[4].val.attrs[2].numVal+' '+att[4].val.attrs[2].unit,
            weight:att[4].val.attrs[3].numVal+' '+att[4].val.attrs[3].unit,
            cost:att[4].val.attrs[4].unit+' '+att[4].val.attrs[4].numVal
        },
        cost:att[5].val.attrs[0].unit+' '+att[5].val.attrs[0].numVal,
        mission_system:{
            communication:att[6].val.attrs[0].strVal,
            reconnaissance:att[6].val.attrs[1].strVal,
            weight:att[6].val.attrs[2].numVal+' '+att[6].val.attrs[2].unit,
            cost:att[6].val.attrs[3].unit+' '+att[6].val.attrs[3].numVal
        },
        ground_support:{
            station:att[7].val.attrs[0].strVal,
            land:att[7].val.attrs[1].strVal,
            transportation_and_storage:att[7].val.attrs[2].strVal,
            inspection_and_maintenance:att[7].val.attrs[3].strVal,
            cost:att[7].val.attrs[4].unit+' '+att[7].val.attrs[4].numVal
        },
        energy_system:{
            photovoltaic_system:att[8].val.attrs[0].strVal,
            energy_storage_battery:att[8].val.attrs[1].strVal,
            max_output:att[8].val.attrs[2].numVal+' '+att[8].val.attrs[2].unit,
            endurance:att[8].val.attrs[3].numVal+' '+att[8].val.attrs[3].unit,
            weight:att[8].val.attrs[4].numVal+' '+att[8].val.attrs[4].unit,
            cost:att[8].val.attrs[5].unit+' '+att[8].val.attrs[5].numVal,
        },
        flight_management_system:{
            flight_control_and_actuation:att[9].val.attrs[0].strVal,
            integrated_management_of_energy_propulsion_and_missions:att[9].val.attrs[1].strVal
        },
        endurance:att[10].val.attrs[0].numVal+' '+att[10].val.attrs[0].unit,
        flight_capacity:{
            take_off_and_landing_requirements:att[11].val.attrs[0].strVal,
            height:att[11].val.attrs[1].numVal+' '+att[11].val.attrs[1].unit,
            speed:att[11].val.attrs[2].numVal+' '+att[11].val.attrs[2].unit,
            flight_range:att[11].val.attrs[3].numVal+' '+att[11].val.attrs[3].unit,
            endurance:att[11].val.attrs[4].numVal+' '+att[11].val.attrs[4].unit,
            flight_service_life:att[11].val.attrs[5].numVal+' '+att[11].val.attrs[5].unit
        }
      }));  
    })
  }
  const ChangePlan=()=>
  {
    //逐一查看数据是否有更新
    const update=[]
    var weight=null
    if(document.getElementById("weight").value==''||document.getElementById("weight").value==state.weight){
      weight = parseFloat(state.weight)
    }else{
      weight = parseFloat(document.getElementById("weight").value)
      update.push("totalWeight")
    }
    var hight=state.flight_capacity.height.slice(0,state.flight_capacity.height.indexOf(' '))
    if(document.getElementById("hight").value==''||document.getElementById("hight").value==hight){
      hight=parseFloat(hight)
    }else{
      hight =parseFloat(document.getElementById("hight").value)
      update.push("flightAltitude")
    }
    var wing=state.aerodynamic_configuration.wing.slice(0,state.aerodynamic_configuration.wing.indexOf(' '))
    if(document.getElementById("wing").value==''||document.getElementById("wing").value==wing){
      wing=parseFloat(wing)
    }else{
      wing=parseFloat(document.getElementById("wing").value)
      update.push("wing")
    }
    var AR=state.aerodynamic_configuration.AR
    if(document.getElementById("AR").value==''||document.getElementById("AR").value==AR){
      AR=parseFloat(AR)
    }else{
      AR=parseFloat(document.getElementById("AR").value)
      update.push("AR")
    }
    //创建需要传递的参数data
    const TransferData={
      plan:{
        flightPerformance: {
          flightAltitude: hight,
          flightSpeed: parseFloat(state.flight_capacity.speed.slice(0,state.flight_capacity.speed.indexOf(' '))),
          flightRange: parseFloat(state.flight_capacity.flight_range.slice(0,state.flight_capacity.flight_range.indexOf(' '))),
          flightEndurance: parseFloat(state.flight_capacity.endurance.slice(0,state.flight_capacity.endurance.indexOf(' ')))
        },
        basicData: {
          totalWeight: weight,
          totalCost: parseFloat(state.cost.slice(state.cost.indexOf(' ')))
        },
        aerodynamicConfiguration: {
          length: parseFloat(state.aerodynamic_configuration.length.slice(0,state.aerodynamic_configuration.length.indexOf(' '))),
          wing: wing,
          AR: AR
        },
        bodyStructure: {
          bodyWeight: parseFloat(state.airframe_structure.weight.slice(0,state.airframe_structure.weight.indexOf(' '))),
          bodyCost: parseFloat(state.airframe_structure.cost.slice(state.airframe_structure.cost.indexOf(' ')))
        },
        energySystem: {
          energyEndurance: parseFloat(state.energy_system.endurance.slice(0,state.energy_system.endurance.indexOf(' '))),
          energyAbility: parseFloat(state.energy_system.max_output.slice(0,state.energy_system.max_output.indexOf(' '))),
          energyWeight: parseFloat(state.energy_system.weight.slice(0,state.energy_system.weight.indexOf(' '))),
          energyCost: parseFloat(state.energy_system.cost.slice(state.energy_system.cost.indexOf(' ')))
        },
        propulsionSystem: {
          propulsionAbility: parseFloat(state.propulsion_system.power.slice(0,state.propulsion_system.power.indexOf(' '))),
          propulsionWeight: parseFloat(state.propulsion_system.weight.slice(0,state.propulsion_system.weight.indexOf(' '))),
          propulsionCost: parseFloat(state.propulsion_system.cost.slice(state.propulsion_system.cost.indexOf(' '))),
        },
        taskSystem: {
          taskWeight: parseFloat(state.mission_system.weight.slice(0,state.mission_system.weight.indexOf(' '))),
          taskCost: parseFloat(state.mission_system.cost.slice(state.mission_system.cost.indexOf(' '))),
        },
        groundSupport: {
          groundCost: parseFloat(state.ground_support.cost.slice(state.ground_support.cost.indexOf(' ')))
        }
      },
      update
    }
    console.log(TransferData)
    const query_2 = gql`
    query CalculateParameters($data: TransferData!) {
      calculatePara(data: $data) {
        msg,
        errorPara,
        plan {
          flightPerformance{
            flightAltitude,
            flightSpeed,
            flightRange,
            flightEndurance
          },
          basicData{
            totalWeight,
            totalCost
          },
          aerodynamicConfiguration{
            length,
            wing,
            AR
          },
          bodyStructure{
            materialType,
            bodyWeight,
            bodyCost
          },
          energySystem{
            energyEndurance,
            energyAbility,
            energyWeight,
            energyCost
          },
          propulsionSystem{
            propulsionAbility,
            propulsionWeight,
            propulsionCost
          },
          taskSystem{
            taskWeight,
            taskCost
          },
          groundSupport{
            groundCost
          }
        },
        change
      }
    }`
    console.log(query_2)
    client_2.query({
      query:query_2,
      variables: {
        data: TransferData
      }
    }).then(result=>{
      const msg = result.data.calculatePara.msg
      if(msg==-1){
        alert('相关参数不匹配，请重新修改参数', 'danger')
      }
      if(msg==-3){
        alert('修改参数过多，相互影响，请减少修改函数个数','danger')
      }
      const errorPara = result.data.calculatePara.errorPara
      const plan = result.data.calculatePara.plan
      const change = result.data.calculatePara.change
      console.log(result.data.calculatePara)
    })
  }
  return (
    <div>
    <div className="offcanvas offcanvas-start show" data-bs-horizontal-width="20px" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel" >
    <div id="liveAlertPlaceholder"></div>
      <div className="offcanvas-header">
        <h5 className="offcanvas-title" id="offcanvasScrollingLabel">系统推荐方案</h5>
      </div>
      <div className="offcanvas-body">
        <div className="input-group mb-3">
          <div className="input-group-prepend">
            <span className="input-group-text" id="basic-addon1">任务载荷</span>
          </div>
          <input type="text" className="form-control" id="load" placeholder="必填" aria-label="Username" aria-describedby="basic-addon1" />
        </div>
        <div className="input-group mb-3">
      <div className="input-group-prepend">
        <span className="input-group-text" id="basic-addon1">航时要求</span>
      </div>
      <input type="text" className="form-control" id="require" placeholder="必填" aria-label="Username" aria-describedby="basic-addon1" />
    </div>
    <div className="input-group mb-3">
      <div className="input-group-prepend">
        <span className="input-group-text" id="basic-addon1">单机成本</span>
      </div>
      <input type="text" className="form-control" id="cost" placeholder="必填" aria-label="Username" aria-describedby="basic-addon1" />
    </div>
    <span style={{display:'flex',justifyContent:'center'}}>
    <button type="button" className="btn btn-outline-primary" onClick={GetPlan}>生成方案</button>
    </span>
    <hr />
    <div className="input-group mb-3">
      <div className="input-group-prepend">
        <span className="input-group-text" id="basic-addon1">重量</span>
      </div>
      <input id="weight" type="text" className="form-control" placeholder="选填" aria-label="Username" aria-describedby="basic-addon1" />
    </div>
    <div className="input-group mb-3">
      <div className="input-group-prepend">
        <span className="input-group-text" id="basic-addon1">高度</span>
      </div>
      <input id="hight"  type="text" className="form-control" placeholder={state.flight_capacity.height} aria-label="Username" aria-describedby="basic-addon1" />
    </div>
    <div className="input-group mb-3">
      <div className="input-group-prepend">
        <span className="input-group-text" id="basic-addon1">展弦比</span>
      </div>
      <input  id="AR"type="text" className="form-control" placeholder={state.aerodynamic_configuration.AR} aria-label="Username" aria-describedby="basic-addon1" />
    </div>
    <div className="input-group mb-3">
      <div className="input-group-prepend">
        <span className="input-group-text" id="basic-addon1">翼展</span>
      </div>
      <input id="wing"type="text" className="form-control" placeholder= {state.aerodynamic_configuration.wing} aria-label="Username" aria-describedby="basic-addon1" />
    </div>
    <span style={{display:'flex',justifyContent:'center'}}>
      <button type="button" className="btn btn-outline-primary" onClick={ChangePlan}>更换方案</button>
    </span>
      </div>
    </div>

    <div className="offcanvas offcanvas-end show" data-bs-horizontal-width="20px" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel" >
    <div className="offcanvas-header">
      <h5 className="offcanvas-title" id="offcanvasScrollingLabel">系统推荐方案</h5>
  
    </div>
    <div className="offcanvas-body">
      <div className="card" >
        <div className="card-header">
          最推荐方案（可放评分）
        </div>
        <ul className="list-group list-group-flush">
          <li className="list-group-item">装载能力:{state.loading_capacity}</li>
          <li className="list-group-item">航时:{state.endurance}</li>
          <li className="list-group-item">任务能力:<br></br>
          &nbsp;&nbsp;任务类型:{state.mission_capacity.type}<br></br>
          &nbsp;&nbsp;任务高度:{state.mission_capacity.height}<br></br>
          &nbsp;&nbsp;任务覆盖范围:{state.mission_capacity.coverage}<br></br></li>
          <li className="list-group-item">飞行能力<br></br>
          &nbsp;&nbsp;高度:{state.flight_capacity.height}<br></br>
          &nbsp;&nbsp;速度:{state.flight_capacity.speed}<br></br>
          &nbsp;&nbsp;航时:{state.flight_capacity.endurance}<br></br>
          &nbsp;&nbsp;航程:{state.flight_capacity.flight_range}<br></br>
          &nbsp;&nbsp;起降要求:{state.flight_capacity.take_off_and_landing_requirements}<br></br>
          &nbsp;&nbsp;飞行使用寿命:{state.flight_capacity.flight_service_life}<br></br>
          </li>
          <li className="list-group-item">
          重量:{state.weight}<br></br>
          成本:{state.cost}<br></br>
          寿命:<br></br></li>
          <li className="list-group-item">气动布局<br></br>
          &nbsp;&nbsp;单机身:{state.aerodynamic_configuration.single_fuselage}<br></br>
          &nbsp;&nbsp;长度:{state.aerodynamic_configuration.length}<br></br>
          &nbsp;&nbsp;翼展:{state.aerodynamic_configuration.wing}<br></br>
          &nbsp;&nbsp;展弦比:{state.aerodynamic_configuration.AR}<br></br></li>
          <li className="list-group-item">机体结构:<br></br>
          &nbsp;&nbsp;结构布局:{state.airframe_structure.layout}<br></br>
          &nbsp;&nbsp;刚度强度:{state.airframe_structure.strength_and_stiffness}<br></br>
          &nbsp;&nbsp;材料:{state.airframe_structure.materials}<br></br>
          &nbsp;&nbsp;工艺:{state.airframe_structure.manufacturing_techniques}<br></br>
          &nbsp;&nbsp;重量:{state.airframe_structure.weight}<br></br>
          &nbsp;&nbsp;成本:{state.airframe_structure.cost}<br></br></li>
          <li className="list-group-item">能源系统:<br></br>
          &nbsp;&nbsp;光伏系统:{state.energy_system.photovoltaic_system}<br></br>
          &nbsp;&nbsp;储存电池:{state.energy_system.energy_storage_battery}<br></br>
          &nbsp;&nbsp;最大输出:{state.energy_system.max_output}<br></br>
          &nbsp;&nbsp;续航:{state.energy_system.endurance}<br></br>
          &nbsp;&nbsp;重量:{state.energy_system.weight}<br></br>
          &nbsp;&nbsp;成本:{state.energy_system.cost}<br></br></li>
          <li className="list-group-item">推进系统<br></br>
          &nbsp;&nbsp;电机:{state.propulsion_system.electric_motor}<br></br>
          &nbsp;&nbsp;螺旋桨:{state.propulsion_system.propeller}<br></br>
          &nbsp;&nbsp;能力:{state.propulsion_system.power}<br></br>
          &nbsp;&nbsp;重量:{state.propulsion_system.weight}<br></br>
          &nbsp;&nbsp;成本:{state.propulsion_system.cost}<br></br></li>
          <li className="list-group-item">任务系统:<br></br>
          &nbsp;&nbsp;通信:{state.mission_system.communication}<br></br>
          &nbsp;&nbsp;侦察:{state.mission_system.reconnaissance}<br></br>
          &nbsp;&nbsp;重量:{state.mission_system.weight}<br></br>
          &nbsp;&nbsp;成本:{state.mission_system.cost}<br></br></li>
          <li className="list-group-item">飞管系统:<br></br>
          &nbsp;&nbsp;飞行控制与作动:{state.flight_management_system.flight_control_and_actuation}<br></br>
          &nbsp;&nbsp;能源、推进、任务综合管理：方案、技术参数与能力:{state.flight_management_system.integrated_management_of_energy_propulsion_and_missions}<br></br></li>
          <li className="list-group-item">地面保障:<br></br>
          &nbsp;&nbsp;地面站:{state.ground_support.station}<br></br>
          &nbsp;&nbsp;起降:{state.ground_support.land}<br></br>
          &nbsp;&nbsp;运输储存:{state.ground_support.transportation_and_storage}<br></br>
          &nbsp;&nbsp;检测维修:{state.ground_support.inspection_and_maintenance}<br></br>
          &nbsp;&nbsp;成本:{state.ground_support.cost}<br></br></li>
        </ul>
      </div>
      <br />
    </div>
  </div>
  </div>
  )
}
function App() {
  return (
    <div>
    <SidebarLeft></SidebarLeft>
    <Model></Model>
    {/* <SidebarRight></SidebarRight> */}
    </div>
  );
}

export default App;
