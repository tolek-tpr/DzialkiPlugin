// Lets say that we have 2 players that are selling their plots
// player 1 has a plot that is 100x100 and its a hospital
// and player 2 has a plot 50x50 that has no function
// I want backend(this) to send the data that it has to the frontend
// And from there it will show all the things that players need to know
// Plus we will be able to (Not by changin sites or if thats too hard changing the id at the end of the URL to a random ID 
// http://<domain>/world/<world>?id=10 for example) show diffrent sales with the buttons NEXT and PREVIOUS
// The button BUY will send a request to the backend for a QR code/ DOGECOING waller the player has submitted when selling the plot
// And the backend (Its not really the backend the backend is the plugin/the server the plugin is hosted on) will check with the main server aka main_backend
// if the sale is still available (The main_backend will communicate with this backend by websockets beacuse its not very hard to implement in java)


// Simple websocket server
import { Server } from 'ws';
import { connect } from 'ngrok';

const wss = new Server({port: 6565})

interface PropertyDesc {
    name: string
    price: number
    x: number
    z: number
    // sizeX: number
    // sizeY: number
}
const properties: { [key: string]: PropertyDesc } = {}

enum ResponseStatus {
    OK,
    INVALID_COMMAND,
    INVALID_ARGS,
}

interface HandlerResponse<T> {
    status: ResponseStatus
    message: string
    payload: T
}

type HandlerFunc<T> = (args: any) => HandlerResponse<T>

const respond = <T>(status: ResponseStatus, message: string, payload: T): HandlerResponse<T> => ({
    status, message, payload
})

const handlerHello: HandlerFunc<any> = (args: object) => respond(ResponseStatus.OK, 'Hello', undefined)

const handlerPutUpForSale: HandlerFunc<any> = (args: PropertyDesc) => {
    properties[args.name] = args
    return respond(ResponseStatus.OK, 'Property put on sale', {})
}

const handlerList: HandlerFunc<typeof properties> = (args: any) => respond(ResponseStatus.OK, 'Available properties', properties)

const handlers: { [key: string]: HandlerFunc<any> } = {
    'hello': handlerHello,
    'put-up-for-sale': handlerPutUpForSale,
    'list': handlerList,
}

wss.on('connection', (ws) => {
    ws.on('message', (json: string) => {
        console.log('Received %s', json)
        const { command, args } = JSON.parse(json)
        console.log("CMD: " + command + " args: " + args)
        const response = undefined === handlers[command]
            ? respond(ResponseStatus.INVALID_COMMAND, 'Invalid command', command)
            : handlers[command](args)
        ws.send(JSON.stringify(response))
    });
    console.log("A new device has connected")
});

const start = async () => {
    const url = await connect(6565);
    console.log(url);
}

start()
