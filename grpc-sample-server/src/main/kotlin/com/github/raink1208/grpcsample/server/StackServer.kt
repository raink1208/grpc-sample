package com.github.raink1208.grpcsample.server

import com.github.raink1208.grpcsample.protos.StackData
import com.github.raink1208.grpcsample.protos.StackSampleGrpcKt
import com.github.raink1208.grpcsample.protos.StackedData
import com.github.raink1208.grpcsample.protos.stackedData
import com.google.protobuf.Empty
import io.grpc.Server
import io.grpc.ServerBuilder

class StackServer(private val port: Int) {
    val server: Server = ServerBuilder
        .forPort(port)
        .addService(StackService())
        .build()

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@StackServer.stop()
                println("*** server shut down")
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    private class StackService: StackSampleGrpcKt.StackSampleCoroutineImplBase() {
        val stackedDataList = mutableListOf<StackData>()
        override suspend fun stack(request: StackData): Empty {
            println(request.name + ":" + "[x:${request.x}, y:${request.y}]")
            stackedDataList.add(request)
            return Empty.getDefaultInstance()
        }

        override suspend fun getAll(request: Empty): StackedData {
            return stackedData {
                data.addAll(stackedDataList)
            }
        }
    }
}

fun main() {
    val port = 50051
    val server = StackServer(port)
    server.start()
    server.blockUntilShutdown()
}