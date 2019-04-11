package part2

import org.zeromq.ZMQ

fun main() {
    val context = ZMQ.context(1)

    val socket = context.socket(ZMQ.PULL)

    println("Starting the puller...")

    socket.bind("tcp://localhost:5897")

    while (true) {

        val rawRequest = socket.recv()

        val cleanRequest = String(rawRequest, 0, rawRequest.size - 1)
        println("pulled some data : $cleanRequest")
    }
}