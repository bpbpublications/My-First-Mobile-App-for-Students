package com.example.flashcards.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.flashcards.MyApp
import com.example.flashcards.R
import com.example.flashcards.adapter.NoteAdapter
import com.example.flashcards.data.Note
import com.example.flashcards.databinding.FragmentHomeBinding
import com.example.flashcards.ui.NoteViewModel
import com.example.flashcards.ui.NoteViewModelFactory
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((requireActivity().application as MyApp).database.noteDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val notesViewPager = binding.notesViewPager
        notesViewPager.setPageTransformer(ViewTransformer())
        val adapter = NoteAdapter { viewNote(it) }
        notesViewPager.adapter = adapter
        lifecycle.coroutineScope.launch {
            viewModel.allNotes.collect() {
                adapter.submitList(it)
            }
        }
        // hide the EmptyMessage
        viewModel.allNotes.asLiveData().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.tvEmptyMessage.visibility = View.VISIBLE
            } else {
                binding.tvEmptyMessage.visibility = View.GONE
            }
        }
    }

    private fun viewNote(note: Note) {
        viewModel.select(note)
        findNavController().navigate(R.id.action_navigation_home_to_viewNoteFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // A ViewPager2 PageTransformer that adds a "depth" effect
    class ViewTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            val height = page.height.toFloat()
            val width = page.width.toFloat()
            val scale = min(if (position < 0.0f) 1.0f else abs(1.0f - position))
            page.scaleX = scale
            page.scaleY = scale
            page.pivotX = width * 0.5f
            page.pivotY = height * 0.5f
            page.translationX = if (position < 0.0f) width * position else -width * position * 0.25f
        }

        companion object {
            private fun min(value: Float, min: Float = .5f): Float {
                return max(value, min)
            }
        }
    }
}