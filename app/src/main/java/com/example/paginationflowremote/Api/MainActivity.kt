package com.example.paginationflowremote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.paginationflowremote.Api.ApiService
import com.example.paginationflowremote.Api.CharacterFactory
import com.example.paginationflowremote.Api.CharacterRepository
import com.example.paginationflowremote.Api.Database.AppDatabase
import com.example.paginationflowremote.Api.Database.CharacterRemoteKeysDao
import com.example.paginationflowremote.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CharactersViewModel
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)

        val characterDao = Room.databaseBuilder(
            this@MainActivity,
            AppDatabase::class.java,
            "character"
            ).fallbackToDestructiveMigration()
            .build()
            .CharacterDao()

        val remoteKeysDao = Room.databaseBuilder(
            this@MainActivity,
            AppDatabase::class.java,
            "character"
            ).fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
            .RemoteKeysDao()

        val repository = CharacterRepository(api, characterDao, remoteKeysDao)
        val factory = CharacterFactory(repository)

        viewModel = ViewModelProvider(this@MainActivity, factory)[CharactersViewModel::class.java]

        initView()
        initRecycler()
    }

    private fun initRecycler() {
        characterAdapter = CharacterAdapter()
        binding.recyclerview.layoutManager =GridLayoutManager(this@MainActivity, 3, GridLayoutManager.VERTICAL, false)
        binding.recyclerview.adapter = characterAdapter
    }

    private fun initView() {
        characterAdapter = CharacterAdapter()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagingData.collect {
                    characterAdapter.submitData(it)
                }
            }
        }
    }
}