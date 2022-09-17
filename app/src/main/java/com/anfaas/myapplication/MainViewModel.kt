package com.anfaas.myapplication

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import java.nio.charset.Charset

class MainViewModel(val context: Context) : ViewModel() {
    private  var quoteList: Array<Quote> = emptyArray()
    private var index:Int =0
    private lateinit var quote:Quote
    private lateinit var fetchLiveDataObject : MutableLiveData<Quote>
    val fetchLiveData: LiveData<Quote>
        get() = fetchLiveDataObject

    init {
        quoteList = loadQuoteFromAssets()
        fetchLiveDataObject = MutableLiveData(quoteList.get(0))
    }

    private fun loadQuoteFromAssets(): Array<Quote> {
        //file locate and add to stream
       val inputStream = context.assets.open("quotes.json")
        //find stream total size
        val size :Int =inputStream.available()
        //buffer to store the string as byteArray
        val buffer = ByteArray(size)
        //read from file and store in buffer
        inputStream.read(buffer)
        //close buffer
        inputStream.close()
        //convert buffer into String and use encoding UTF8
        val json = String(buffer,Charsets.UTF_8)
        //create gson object
        val gson = Gson()
        //retrun array<Quotes> by serialzing the string
      return  gson.fromJson(json,Array<Quote>::class.java)
    }

     fun updateText()
    {

        quote = Quote(quoteList[index].text,quoteList[index].author)
        fetchLiveDataObject.value = quote
    }
    fun nextQuote()
    {
        index++
        updateText()
    }
    fun prevQuote()
    {
        index--
        updateText()
    }
}