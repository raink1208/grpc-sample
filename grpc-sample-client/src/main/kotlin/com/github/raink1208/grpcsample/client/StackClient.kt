package com.github.raink1208.grpcsample.client

import com.github.raink1208.grpcsample.protos.StackSampleGrpcKt
import com.github.raink1208.grpcsample.protos.stackData
import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class StackClient(private val channel: ManagedChannel, val player: String): Closeable {
    private val stub: StackSampleGrpcKt.StackSampleCoroutineStub = StackSampleGrpcKt.StackSampleCoroutineStub(channel)

    suspend fun addData(x: Int, y: Int) {
        val request = stackData {
            name = player
            this.x = x
            this.y = y
        }
        stub.stack(request)
    }

    suspend fun getStackedData() {
        val response = stub.getAll(Empty.getDefaultInstance())
        println("count: " + response.dataCount)
        for (i in response.dataList) {
            println(i.name + ":" + i.x + ":" + i.y)
        }
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

suspend fun main() {
    val port = 50051
    val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()
    val name = readLine() ?: "test"
    val client = StackClient(channel, name)

    while (true) {
        val input = readLine() ?: ""
        if (input == "") break
        val data = input.split(" ").map { it.toInt() }
        client.addData(data[0], data[1])
    }
    client.getStackedData()

    client.close()
}