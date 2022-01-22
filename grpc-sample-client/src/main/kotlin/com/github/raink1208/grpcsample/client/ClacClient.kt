package com.github.raink1208.grpcsample.client

import com.github.raink1208.grpcsample.protos.ClacSampleGrpcKt
import com.github.raink1208.grpcsample.protos.clacRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.util.concurrent.TimeUnit

class ClacClient(private val channel: ManagedChannel): Closeable {
    val stub: ClacSampleGrpcKt.ClacSampleCoroutineStub = ClacSampleGrpcKt.ClacSampleCoroutineStub(channel)

    suspend fun plus(x: Double, y: Double): Double {
        val request = clacRequest {
            this.x = x; this.y = y
        }
        val response = stub.plus(request)
        return response.result
    }

    suspend fun minus(x: Double, y: Double): Double {
        val request = clacRequest {
            this.x = x; this.y = y
        }
        val response = stub.minus(request)
        return response.result
    }

    suspend fun multiply(x: Double, y: Double): Double {
        val request = clacRequest {
            this.x = x; this.y = y
        }
        val response = stub.multiply(request)
        return response.result
    }

    suspend fun divide(x: Double, y: Double): Double {
        val request = clacRequest {
            this.x = x; this.y = y
        }
        val response = stub.divide(request)
        return response.result
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

suspend fun main() {
    val port = 50051
    val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()
    val client = ClacClient(channel)
    client.plus(10.0 ,20.0)
}