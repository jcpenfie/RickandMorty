package dam.jcpf.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dam.jcpf.rickandmorty.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.episodesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        // TODO: Obtener con retrofit los episodios
    binding.episodesRecyclerview.adapter =
        MyAdapter (loadEpisodesFromRetrofit()){ selectedEpisode ->

            val bundle = Bundle()
            bundle.putString("name", selectedEpisode.name)
            bundle.putString("episode", selectedEpisode.episode)
            bundle.putString("airDate", selectedEpisode.air_date)
            findNavController().navigate(R.id.detailsFragment)
        }
    }

    private fun loadEpisodesFromRetrofit(): Any{
        return TODO("Obtener json de retrofit")
    }
}