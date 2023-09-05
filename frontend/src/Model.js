// 造飞机：https://www.jq22.com/jquery-info17198

import React, { Component } from 'react'
import * as THREE from 'three'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader';
// import { OrbitControls } from 'three/addons/controls/OrbitControls.js';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';




export default class Model extends Component {

    componentDidMount() {
        this.init()
    }

    init = () => {

        const scene = new THREE.Scene()

        const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 1000)

        const renderer = new THREE.WebGLRenderer()
        renderer.setSize(window.innerWidth, window.innerHeight)
        renderer.render(scene, camera)
        document.getElementById('stage').appendChild(renderer.domElement)

        // const geometry = new THREE.BoxGeometry( 1, 1, 1 );
        // const material = new THREE.MeshBasicMaterial( { color: 0x00ff00 } );
        // const cube = new THREE.Mesh( geometry, material );
        // scene.add( cube );
        const loader = new GLTFLoader();
        let model;
        loader.load("plane.gltf", function (gltf) {
            model =gltf.scene
            console.log("控制台查看加载gltf文件返回的对象结构", gltf);
            console.log("gltf对象场景属性", gltf.scene);
            // 返回的场景对象gltf.scene插入到threejs场景中
            scene.add(gltf.scene);
            // model.scale.set(model.scale.x,model.scale.y,2*model.scale.z)
            gltf.scene.traverse(function (child) {
                if (child.isMesh) {
                    child.frustumCulled = false;
                    //模型阴影
                    child.castShadow = true;
                    //模型自发光
                    child.material.emissive = child.material.color;
                    child.material.emissiveMap = child.material.map;
                }
            })
            // 光源
            const light = () => {
                const pointLight = new THREE.PointLight(0xffffff, 1.0);
                pointLight.position.set(50, 50, 100);
                const pointLightHelper = new THREE.PointLightHelper(pointLight, 10);
                scene.add(pointLightHelper);
                scene.add(pointLight);
            };
            light();
            // 添加鼠标控制
            const controls = new OrbitControls(camera, renderer.domElement);

            // 设置控制目标为模型
            controls.target.set(0, 0, 0);

            // gltf.scene.scale.set(20, 20, 20); 
            // gltf.scene.position.set(0,0,0);
            // const cube = gltf.scene.getObjectByName("Cube")
            // // cube.scale.set(1,1,1)
            // cube.position.set(0,0,0)
            // console.log("cube的物体信息",cube)

            function animate() {
                requestAnimationFrame(animate);
    
                // cube.rotation.x += 0.01;
                // cube.rotation.y += 0.01;
                // 旋转模型
                // if (model) {
                //     model.rotation.x += 0.005;
                //     model.rotation.y += 0.005;
                // }
                controls.update();
                renderer.render(scene, camera);
            };
    
            animate();
        });

        const axesHelper = new THREE.AxesHelper(10)
        scene.add(axesHelper)
        camera.position.x = 5
        // camera.position.y =0
        camera.position.z = 10;
        // function animate() {
        //     requestAnimationFrame(animate);

        //     // cube.rotation.x += 0.01;
        //     // cube.rotation.y += 0.01;
        //     // 旋转模型
        //     // if (model) {
        //     //     model.rotation.x += 0.005;
        //     //     model.rotation.y += 0.005;
        //     // }
        //     // controls.update();
        //     renderer.render(scene, camera);
        // };

        // animate();
    }
    render() {
        return (
            <div id="stage">
            </div>
        )
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
