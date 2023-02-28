package fr.openium.mastertp1_2.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.openium.mastertp1_2.R
import fr.openium.mastertp1_2.adapter.CustomAdapter
import fr.openium.mastertp1_2.model.Meme
import fr.openium.mastertp1_2.model.MemeResponse
import fr.openium.mastertp1_2.utils.IntentIntegrator
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.*


class ListFragment : Fragment(), CustomAdapter.AdapterListener {

    private var adapter: CustomAdapter? = null
    private var arrayList: ArrayList<String> = arrayListOf<String>()
    private var arrayListMeme = ArrayList<Meme>()
    private var intentIntegrator: IntentIntegrator? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intentIntegrator = IntentIntegrator(this)

        setFragmentResultListener(FragmentAdd.KEY_REQUEST) { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            var todoId :Int? = null
            if (bundle.get(FragmentAdd.KEY_ID)!=null) {
                todoId = bundle.getInt(FragmentAdd.KEY_ID)
            }

            val todoTitle = bundle.getString(FragmentAdd.KEY_TITLE)
            addOrModifyToListAndRefresh(todoId, todoTitle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById<RecyclerView>(R.id.fragment_list_RecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        loadDataFromNetwork();
        //adapter = CustomAdapter(arrayListMeme)

        //recyclerView?.adapter = adapter

        var buttonScan = view.findViewById<Button>(R.id.fragment_list_button_scan)
        buttonScan?.setOnClickListener {

            var alertDialog = intentIntegrator?.initiateScan()
            alertDialog?.show()
        }

        var buttonAdd = view.findViewById<Button>(R.id.fragment_list_button_add)
        buttonAdd.setOnClickListener {
            goToFragmentAdd(null,null)
        }
    }

    private fun goToFragmentAdd(position: Int? ,titre: String?) {
        val navController = findNavController()
        val bundle = bundleOf(Pair(FragmentAdd.KEY_ID,position),
            Pair(FragmentAdd.KEY_TITLE,titre))
        navController.navigate(R.id.action_listFragment_to_fragmentAdd,bundle)
    }

    private fun addOrModifyToListAndRefresh(position:Int?,data: String?) {
        if (position != null){
            arrayList.set(position,data!!)
        }else{
            arrayList.add(data.toString())
        }
        adapter?.notifyDataSetChanged()
    }

    private fun loadDataFromNetwork(){
        CoroutineScope(Dispatchers.IO).launch {

            val client = HttpClient(CIO)
            val response: HttpResponse = client.get("https://api.imgflip.com/get_memes")
            println(response.status)
            val gson = Gson()
            val data: String = response.bodyAsText()
            val memeResponse: MemeResponse = gson.fromJson(data, MemeResponse::class.java)

            arrayListMeme = memeResponse.data.memesList as ArrayList<Meme>

            client.close()
            withContext(Dispatchers.Main){
                adapter = CustomAdapter(arrayListMeme)
                recyclerView?.adapter = adapter
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        addOrModifyToListAndRefresh(null,intentResult.contents)
    }

    override fun onClick(position: Int) {
        goToFragmentAdd(position,arrayList.get(position))
    }
}