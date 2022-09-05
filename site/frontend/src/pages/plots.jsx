import React from "react";

import './plots.css';
// In img src="" in the quotes there will be a call to the backend to get all the data (Size, Type, Player Avatar)
// And the buy button will take you to a page with a QR code and a dogecoin wallet adress.
// next previous and buy buttons will make a call to the backend that will give the frontend all the information it needs
// And the backend will be updating its database every ~2min from every running plugin
function nxt() {
    console.log(
        "Next"
    )
}

function prev() {
    console.log("Previous")
}

function buy() {

}

const websocket = new WebSocket("ws://localhost:6565")
if (websocket) console.log("test_connected")
websocket.onmessage = function (messageEvent) {
    var wsMsg = messageEvent.data;
    console.log("WebSocket MESSAGE: " + wsMsg);
    
};
console.log(websocket)

const commandList = {
    command: "list",
    args: [],
}
const sell = {
    command: "put-up-for-sale",
    args: ["DUPA1"]
}
function getPlots() {
    websocket.send(JSON.stringify(commandList))
    console.log("test")
}
function makeNewSale() {
    websocket.send(JSON.stringify(sell))
}


const plots = () => (
    <div className='plots'>
        <button id='nxt' onClick={nxt}>Next</button>
        <button id='prev' onClick={prev}>Previous</button>

        <button id='buy' onClick={buy}>BUY</button>
        <button id='getPlots' onClick={getPlots}>Get Plots</button>
        <button id='make_sale' onClick={makeNewSale}>Make New Sale</button>

        <p>Size: 100x100, Hospital</p>
        <img src="#"/>
    </div>
)

export default plots;
export {getPlots};