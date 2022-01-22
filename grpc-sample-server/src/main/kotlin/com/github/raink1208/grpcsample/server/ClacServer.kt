package com.github.raink1208.grpcsample.server

import com.github.raink1208.grpcsample.protos.ClacRequest
import com.github.raink1208.grpcsample.protos.ClacResponse
import com.github.raink1208.grpcsample.protos.ClacSampleGrpcKt
import com.github.raink1208.grpcsample.protos.clacResponse
import io.grpc.Server
import io.grpc.ServerBuilder

class ClacServer {
    val server: Server = ServerBuilder
        .forPort(50051)
        .addService(ClacServerService())
        .build()

    fun start() {
        server.start()
        Runtime.getRuntime().addShutdownHook(
            Thread {
                this@ClacServer.stop()
            }
        )
    }

    private fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    private class ClacServerService: ClacSampleGrpcKt.ClacSampleCoroutineImplBase() {
        override suspend fun plus(request: ClacRequest): ClacResponse {
            println("${request.x} + ${request.y} = ${request.x + request.y}")
            return clacResponse { result = request.x + request.y }
        }

        override suspend fun minus(request: ClacRequest): ClacResponse {
            println("${request.x} - ${request.y} = ${request.x - request.y}")
            return clacResponse { result = request.x - request.y }
        }

        override suspend fun multiply(request: ClacRequest): ClacResponse {
            println("${request.x} * ${request.y} = ${request.x * request.y}")
            return clacResponse { result = request.x / request.y }
        }

        override suspend fun divide(request: ClacRequest): ClacResponse {
            if (request.y == 0.0) return clacResponse { result = 0.0 } //error
            println("${request.x} / ${request.y} = ${request.x / request.y}")
            return clacResponse { result = request.x / request.y }
        }
    }
}

fun main() {
    val server = ClacServer()
    server.start()
    server.blockUntilShutdown()
}