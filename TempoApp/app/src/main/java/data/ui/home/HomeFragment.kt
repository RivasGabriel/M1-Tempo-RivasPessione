class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: TempoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(TempoViewModel::class.java)

        observeData()
        setOnClickListeners()
    }

    private fun observeData() {
        viewModel.currentTempoDay.observe(viewLifecycleOwner) { currentTempoDay ->
            binding.tvCurrentTempoDay.text = currentTempoDay?.toString()
        }

        viewModel.remainingDays.observe(viewLifecycleOwner) { remainingDays ->
            binding.tvRemainingDays.text = remainingDays.toString()
        }

        viewModel.isDataLoading.observe(viewLifecycleOwner) { isDataLoading ->
            if (isDataLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnRefresh.setOnClickListener {
            viewModel.refreshData()
        }

        binding.btnSetReminder.setOnClickListener {
            val currentTempoDay = viewModel.currentTempoDay.value
            if (currentTempoDay != null) {
                TempoNotification.scheduleNotification(requireContext(), currentTempoDay)
            }
        }
    }
}
