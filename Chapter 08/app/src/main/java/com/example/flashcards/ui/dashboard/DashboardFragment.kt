package com.example.flashcards.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.flashcards.MyApp
import com.example.flashcards.R
import com.example.flashcards.data.Note
import com.example.flashcards.databinding.FragmentDashboardBinding
import com.example.flashcards.ui.NoteViewModel
import com.example.flashcards.ui.NoteViewModelFactory

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as MyApp).database.noteDao())
    }
    lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabSave.setOnClickListener { addNote() }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.editNoteTitle.text.toString(),
            binding.editNoteContent.text.toString()
        )
    }

    private fun addNote() {
        if (isEntryValid()) {
            viewModel.addNote(
                binding.editNoteTitle.text.toString(),
                binding.editNoteContent.text.toString()
            )
            switchToNotes()
        } else {
            Toast.makeText(requireContext(), R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    private fun switchToNotes() {
        Toast.makeText(requireContext(), R.string.note_saved, Toast.LENGTH_LONG).show()
        binding.editNoteTitle.text!!.clear()
        binding.editNoteContent.text!!.clear()
        findNavController().navigate(R.id.navigation_home)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}