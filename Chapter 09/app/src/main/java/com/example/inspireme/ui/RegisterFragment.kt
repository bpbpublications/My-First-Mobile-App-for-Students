package com.example.inspireme.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.inspireme.MainActivity
import com.example.inspireme.databinding.FragmentRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    val binding get() = _binding!!
    private val auth by lazy { Firebase.auth }
    private val vm: CloudAuthViewModel by activityViewModels { CloudAuthViewModelFactory(auth) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.vm = vm
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onStart() {
        super.onStart()
        vm.updateUserAuthState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.authState.observe(viewLifecycleOwner) {
            if (it == AuthState.AUTHENTICATED) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }
        binding.apply {
            btnRegister.setOnClickListener {
                vm.performRegistration(
                    editUsername.text.toString(),
                    editRegEmail.text.toString(),
                    editRegPassword.text.toString(),
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}