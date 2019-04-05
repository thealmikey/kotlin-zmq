import org.zeromq.ZMQ


    fun main() {
        val context = ZMQ.context(1)
        //our client will be the one sending the request to the server
        //so it will implement the Request socket
        val socket = context.socket(ZMQ.REQ)
        println("connecting to hello world server...")
        socket.connect("tcp://localhost:5897")
        //we will send 10 request to the server and get the responses from the server
        for (i in 1..10) {
            //we first need to convert our string to a byte array
            //although not necessary, i will create two variables to help differentiate the request on the wire and the one we print out through our example.
            var plainRequest = "Hello "
            var byteRequest = plainRequest.toByteArray()
            // and then set the last delimiter byte to 0
            byteRequest[byteRequest.size - 1] = 0
            //we'll log to see each request as it is sent
            println("sending request $i $plainRequest")
            socket.send(byteRequest, 0)

            //after sending the request we get the reply
            //by listening on the socket
            val byteReply = socket.recv(0)
            //we convert the bytearray we receive to plain text so we can print it out
            var plainReply = String(byteReply, 0, byteReply.size - 1)

            println("Received reply $plainReply")


        }
    }
