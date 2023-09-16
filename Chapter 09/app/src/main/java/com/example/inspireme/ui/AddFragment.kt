package com.example.inspireme.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.inspireme.AuthActivity
import com.example.inspireme.databinding.FragmentAddBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val auth by lazy { Firebase.auth }
    private val fs by lazy { Firebase.firestore }
    private val firebaseViewModel: FirebaseViewModel by activityViewModels {
        FirebaseViewModelFactory(auth, fs)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.vm = firebaseViewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        firebaseViewModel.updateUserAuthState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseViewModel.authState.observe(viewLifecycleOwner) {
            if (it != AuthState.AUTHENTICATED) {
                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finish()
            }
        }
        firebaseViewModel.postUploadStatus.observe(viewLifecycleOwner) {
            if (it == CloudUploadStatus.IN_PROGRESS) {
                binding.tvPostStatus.text = "uploading post to cloud ☁️"
            }else if (it == CloudUploadStatus.SUCCESS) {
                binding.editPostTitle.text?.clear()
                binding.editPostContent.text?.clear()
            }
        }
        binding.fabSave.setOnClickListener {
            firebaseViewModel.addNewPost(
                binding.editPostTitle.text.toString(),
                binding.editPostContent.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}