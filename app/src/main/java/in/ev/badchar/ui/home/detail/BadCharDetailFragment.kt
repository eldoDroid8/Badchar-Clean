package `in`.ev.badchar.ui.home.detail

import `in`.ev.badchar.BR
import `in`.ev.badchar.databinding.FragmentBadCharDetailBinding
import `in`.ev.domain.model.Character
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs


class BadCharDetailFragment : Fragment() {
    private val args: BadCharDetailFragmentArgs by navArgs()
    private var character: Character ?= null
    private val charDetailViewModel: BadCharDetailViewModel by viewModels()
    private var homeDetailBinding: FragmentBadCharDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeDetailBinding = FragmentBadCharDetailBinding.inflate(inflater, container, false)
        character = args.badCharSelected
        character?.let {
            charDetailViewModel.showCharacterDetails(it)
        }
        return homeDetailBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeDetailBinding?.setVariable(BR.viewModel, charDetailViewModel)
        homeDetailBinding?.executePendingBindings()
    }


}