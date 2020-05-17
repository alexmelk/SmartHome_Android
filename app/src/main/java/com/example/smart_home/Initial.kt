package com.example.smart_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_initial.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

@ExperimentalStdlibApi
class Initial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)
    }

    var port = 6510
    var mes = "initial"
    var answer = ""
    var ip = Thread(Runnable(){tryToReceive()})

    fun tryToConnect()
    {
        ip.setDaemon(true)
        ip.start()

        for(i in 0..1000)
        {
            if(answer!="")
            {
                button2.visibility = View.INVISIBLE
                break
            }
            Thread.sleep(5);
        }
        if(button2.visibility==View.INVISIBLE)
        {
            var t = Toast.makeText(this,answer,Toast.LENGTH_LONG)
            t.show()
        }
        else
        {
            var t = Toast.makeText(this,"Не удалось подключиться",Toast.LENGTH_LONG)
            t.show()
            ip = Thread(Runnable(){tryToReceive()})
        }
    }

    private fun tryToReceive() {
        Log.d("tag", "cikl3")
        sendUdp()
        Log.d("tag", "cikl4")
        Thread.sleep(100);

        for(i in 0..10) {
            if(answer=="") {
                Log.d("tag", "cikl5")
                receiveUdp()
                Thread.sleep(500)
                Log.d("tag", "cikl6")
           }else
                break
        }

    }




    fun onClick(View: View)
    {
        Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show()

        tryToConnect()

        if(answer!="")
        {
            var intent = Intent(this,MainActivity::class.java)
            intent.putExtra("ip",answer)
            startActivity(intent)
        }
    }


    fun sendUdp()
    {
        try {

            var socket = DatagramSocket();
            socket.broadcast = true
            var byte = mes.toByteArray()
            var packet =
                DatagramPacket(byte, byte.size, InetAddress.getByName("255.255.255.255"), port)
            socket.send(packet)
            socket.close();
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    fun receiveUdp()
    {
        try {
            var socket = DatagramSocket(port)
            var bytearray = ByteArray(256)
            var bytearray1 = ByteArray(256)
            var packet = DatagramPacket(bytearray, bytearray.size)
            socket.receive(packet)

            if (bytearray != bytearray1) {
                var str = bytearray.decodeToString(0, 256, false)
                if( str.indexOf(mes)==-1)
                {
                    answer = str
                    Log.d("answer", answer)
                }
                Log.d("tag", str)
            }
            socket.close();

        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }


}
