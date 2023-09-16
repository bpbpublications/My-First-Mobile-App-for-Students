package com.example.statetriviachapter4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.statetriviachapter4.adapters.StateAdapter
import com.example.statetriviachapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the data
        val stateList = getStateData()
        // create the adapter
        val stateAdapter = StateAdapter(stateList) {
            val query = "https://www.google.com/search?q=${it.name}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
            startActivity(intent)
        }
        // set the adapter to the recycler view
        binding.stateListView.adapter = stateAdapter
        // set the layout manager to the recycler view
        binding.stateListView.layoutManager = LinearLayoutManager(this)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // add menu options for different layout styles
        menu?.add(0, 0, 0, "List View")
        menu?.add(0, 1, 0, "Grid View")
        menu?.add(0, 2, 0, "Staggered View")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // set the layout manager to the recycler view
        when (item.itemId) {
            0 -> {
                binding.stateListView.layoutManager = LinearLayoutManager(this)
            }
            1 -> {
                binding.stateListView.layoutManager = GridLayoutManager(this, 2)
            }
            2 -> {
                binding.stateListView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}