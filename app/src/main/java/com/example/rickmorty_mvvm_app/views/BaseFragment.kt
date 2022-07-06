package com.example.rickmorty_mvvm_app.views


import android.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickmorty_mvvm_app.viewmodel.RickAndMortyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    protected val rickAndMortyViewModel by lazy {
        ViewModelProvider(requireActivity())[RickAndMortyViewModel::class.java]
    }

    protected fun showError(message: String,action:()->Unit){
        AlertDialog.Builder(requireContext())
            .setTitle("Error has occurred")
            .setMessage(message)
            .setPositiveButton("Retry"){ dialog, _->
                dialog.dismiss()
            }
    }

}