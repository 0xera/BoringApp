package com.example.boringapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import awesomeProject.AwesomeProject
import com.example.boringapp.ui.theme.BoringAppTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.ticker
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            BoringAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column {


                        Text("Text: ${AwesomeProject.getString()}")
                        Text("Sum: ${AwesomeProject.sum(1, 5)}")
                        try {
                            AwesomeProject.exception()
                        } catch (t: Throwable) {
                            Text("Error: $t")
                        }

                        var textFromServer by remember { mutableStateOf("") }
                        LaunchedEffect(Unit) {
                            launch(Dispatchers.IO) {
                                try {
                                    AwesomeProject.runServer("8080")
                                    val url = URL("http://localhost:8080/");
                                    val connection = url.openConnection()
                                    connection.doInput = true
                                    textFromServer =
                                        connection.getInputStream().bufferedReader().readText()

                                } catch (t: Throwable) {
                                    val su = Runtime.getRuntime().exec("netstat -vat ")
                                    textFromServer = su.inputStream.bufferedReader().readText()
                                } catch (e: Throwable) {
                                }
                            }
                        }
                        Text("Text from server: $textFromServer")

                        var tick by remember { mutableStateOf(1) }
                        val rememberCoroutineScope = rememberCoroutineScope()
                        rememberCoroutineScope.launch(Dispatchers.Default) {
                            val ticker = ticker(1000)
                            ticker.consumeEach {
                                tick++

                            }

                        }
                        Text(text = "Tiks: $tick")
                        rememberCoroutineScope.launch(
                            Executors.newSingleThreadExecutor().asCoroutineDispatcher()
                        ) {
                            AwesomeProject.runMillionGoroutines()
                        }

                        val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
//                        rememberCoroutineScope.launch(dispatcher) {
//                            for (i in 0..1_000_000) {
//                                this.coroutineContext[Job]
//                                launch(dispatcher) {
//                                    Log.d("MCWork", i.toString())
//                                }
//                            }
//                        }


                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BoringAppTheme {
        Greeting("Android")
    }
}