class TempoListAdapter(private val days: List<TempoDay>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<TempoListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(day: TempoDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTempoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day)
        holder.itemView.setOnClickListener { listener.onItemClick(day) }
    }

    override fun getItemCount() = days.size

    inner class ViewHolder(private val binding: ItemTempoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: TempoDay) {
            binding.tempoDate.text = day.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            binding.tempoColor.setImageResource(day.color.imageRes)
        }
    }
}
