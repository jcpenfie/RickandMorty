package dam.jcpf.rickandmorty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam.jcpf.rickandmorty.databinding.EpisodeItemBinding

class MyAdapter(
    private val characterList: List<Episodes>,
    private val onClick: (Episodes) -> Unit
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            EpisodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bin(characterList[position])
    }

    /**
     * Devuelve el número total de Pikminis que hay en la lista
     *
     * @return Devuelve un Int
     */
    override fun getItemCount(): Int {
        return characterList.size
    }

    /**
     * Aquí inflamos los cards de la vista ActivityMain para mostrar los datos de los episodios
     *
     * @params binding del layout donde está la card de los pikmins del activityMain
     */
    inner class MyViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bin(episode: Episodes) {
            binding.episodeName.text = episode.name
            binding.episodeAirDate.text = episode.air_date

            binding.root.setOnClickListener {
                onClick(episode)
            }
        }
    }
}