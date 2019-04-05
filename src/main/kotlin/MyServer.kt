import org.zeromq.ZMQ

fun main() {
    val context = ZMQ.context(1)
    //In this example we'll be using two sockets that go together which will be REQUEST and REPLY sockets (ZMQ.REP and ZMQ.REQ)
    //Our server will be doing the replying to the requests from the client, so it will implement the Reply Socket
    val socket = context.socket(ZMQ.REP)
    println("Starting the server...")
    //we bind to a free socket
    //in case your socket is being used you'll get this
    //kind of error
    // Exception in thread "main" org.zeromq.ZMQException: Errno 48 : Address already in use
    //in this case, in my case i just add one to the last number so if 5555 is in use by another program
    // , i try 5556 and 5557 and so on
    // and remember to also change the port on the client side if you change it on the server
    socket.bind("tcp://*:5897")

    //we create an infite loop - for me this step
    //always feels unintuitive due to the lessons you learn as a beginner to always ensure your loops terminate
    //when doing network programming, connections are modelled as infinite loops and breaking or finalizing the connection is more frowned upon
    while (true) {
        //recv takes an argument telling us whether the socket should block or not
        val rawRequest = socket.recv(0)
        //messages sent on zeromq are zero terminated. In our case we'll be using a string and we'll omit this last zero by ommiting the last byte from the request
        val cleanRequest = String(rawRequest, 0, rawRequest.size - 1)
        println("Request received : $cleanRequest")
        var plainReply = "World "
        var rawReply = plainReply.toByteArray()
        //we then set the last byte of the reply to 0
        rawReply[rawReply.size - 1] = 0
        socket.send(rawReply, 0)
    }
}