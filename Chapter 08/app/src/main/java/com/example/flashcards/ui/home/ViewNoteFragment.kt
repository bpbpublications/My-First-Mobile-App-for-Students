package com.example.flashcards.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.flashcards.MyApp
import com.example.flashcards.R
import com.example.flashcards.databinding.FragmentViewNoteBinding
import com.example.flashcards.ui.NoteViewModel
import com.example.flashcards.ui.NoteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class ViewNoteFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentViewNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as MyApp).database.noteDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewNoteBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        lifecycle.coroutineScope.launch {
            viewModel.getNote().collect() {
                binding.note = it
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDelete.setOnClickListener {
            viewModel.selectedNote.value?.let { viewModel.deleteNote(it) }
            Toast.makeText(requireContext(), R.string.note_deleted, Toast.LENGTH_SHORT).show()
            dismiss()
        }
        binding.btnEdit.setOnClickListener {
            showEditDialog()
        }
    }

    private fun showEditDialog() {
        findNavController().navigate(R.id.action_viewNoteFragment_to_editNoteFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}