package part2

import org.zeromq.ZMQ

fun main() {
    val context = ZMQ.context(1)

    val socket = context.socket(ZMQ.PUSH)
    println("connecting to a pulling client...")
    socket.connect("tcp://localhost:5897")

    for (i in 1..20) {

        Thread.sleep(100)
        var plainRequest = "Hello "
        var byteRequest = plainRequest.toByteArray()

        byteRequest[byteRequest.size - 1] = 0

        println("sending push $i $plainRequest")
        socket.send(byteRequest, 0)
    }
}
