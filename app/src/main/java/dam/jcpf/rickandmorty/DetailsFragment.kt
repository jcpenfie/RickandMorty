package dam.jcpf.rickandmorty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dam.jcpf.rickandmorty.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private val db = Firebase.firestore
    private val userId = FirebaseAuth.getInstance().currentUser!!.uid
    private var episodeId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Donde trabajamos con los datos de detalles
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodeId = arguments?.getInt("id") ?: 0
        binding.episodeName.text = arguments?.getString("name")
        binding.episodeCode.text = arguments?.getString("episode")
        binding.episodeAirDate.text = arguments?.getString("airDate")

        loadEpisodiosVisto()

        binding.swtichVisto.setOnCheckedChangeListener { _, isChecked ->
            val data = mapOf("visto" to isChecked)
            db.collection("users").document(userId).collection("episodios")
                .document(episodeId.toString()).set(data)
        }

        //todo: hacer
//        lifecycleScope.launch {
//            val characters = loadCharacters(arguments?.getStringArray("characters")!!)
//            binding.charactersRecycler.adapter = CharactersAdapter(characters)
//        }

    }

    private fun loadEpisodiosVisto() {
        db.collection("users")
            .document(userId)
            .collection("episodios")
            .document(episodeId.toString())
            .get()
            .addOnSuccessListener { document ->
                val visto = document.getBoolean("viewed") ?: false
                binding.swtichVisto.isChecked = visto
            }
    }
}