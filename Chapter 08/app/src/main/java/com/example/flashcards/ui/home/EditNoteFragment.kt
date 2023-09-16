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
import com.example.flashcards.databinding.FragmentEditNoteBinding
import com.example.flashcards.ui.NoteViewModel
import com.example.flashcards.ui.NoteViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class EditNoteFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as MyApp).database.noteDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        lifecycle.coroutineScope.launch {
            viewModel.getNote().collect() {
                binding.note = it
            }
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabUpdate.setOnClickListener {
            updateNote()
            Toast.makeText(requireContext(), R.string.note_updated, Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.editNoteTitle.text.toString(),
            binding.editNoteContent.text.toString()
        )
    }

    private fun updateNote() {
        if (isEntryValid()) {
            viewModel.editNote(
                viewModel.selectedNote.value!!.id,
                binding.editNoteTitle.text.toString(),
                binding.editNoteContent.text.toString()
            )
        }
    }
}