package dam.jcpf.rickandmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dam.jcpf.rickandmorty.databinding.FragmentListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var allEpisodes: List<Episodes> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.episodesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        // Lanzar corrutina
        lifecycleScope.launch {
            allEpisodes = loadEpisodesFromRetrofit()
            showEpisodes(allEpisodes)
        }

        binding.swtichSeen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
//                val vistos = allEpisodes.filter { isVisto(it.id)}
//                showEpisodes(vistos)
            } else {
                showEpisodes(allEpisodes)
            }
        }
    }

    private fun showEpisodes(episodes: List<Episodes>) {
        binding.episodesRecyclerview.adapter = MyAdapter(episodes) { selectedEpisode ->
            val bundle = Bundle().apply {
                putInt("id", selectedEpisode.id)
                putString("name", selectedEpisode.name)
                putString("episode", selectedEpisode.episode)
                putString("airDate", selectedEpisode.air_date)
            }
            findNavController().navigate(R.id.detailsFragment, bundle)
        }
    }

    private suspend fun loadEpisodesFromRetrofit(): List<Episodes> {
        return withContext(Dispatchers.IO) {
            val service = RetrofitInstance.getRetroftInstance()
            val response = service.getEpisodes().execute() // Ejecuta síncronamente

            if (response.isSuccessful) {
                response.body()?.results ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}