package ro.ps.proiect.presenter.presenter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.select.Select;
import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.Bilet;
import ro.ps.proiect.model.data_structures.Gara;
import ro.ps.proiect.model.data_structures.OrarGara;
import ro.ps.proiect.model.data_structures.Tren;
import ro.ps.proiect.model.repository.*;
import ro.ps.proiect.presenter.dto.*;
import ro.ps.proiect.presenter.dto.mapper.BiletMapper;
import ro.ps.proiect.presenter.dto.mapper.GaraMapper;
import ro.ps.proiect.presenter.dto.mapper.TrenMapper;
import ro.ps.proiect.presenter.dto.mapper.VagonMapper;
import ro.ps.proiect.presenter.gui_interfaces.I_BiletView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class BiletPresenter {

    private final BiletRepository biletRepository;
    private final GaraRepository garaRepository;
    private final OrarGaraRepository orarGaraRepository;
    private final TrenRepository trenRepository;
    private final VagonRepository vagonRepository;
    private I_BiletView i_biletView;
    private final Grid<BiletDTO> bileteGrid = new Grid<>();
    private final Select<GaraDTO> garaDePlecareSelect = new Select<>();
    private final Select<GaraDTO> garaDeDestinatieSelect = new Select<>();
    private final Select<OreDTO> oraDePlecareSiSosireSelect = new Select<>();
    private final Select<VagonDTO> vagonSelect = new Select<>();
    private final Select<TrenDTO> filterTrenSelect = new Select<>();
    private final Select<GaraDTO> filterGaraDeDestinatieSelect = new Select<>();

    public BiletPresenter(BiletRepository biletRepository, GaraRepository garaRepository, OrarGaraRepository orarGaraRepository,
                          TrenRepository trenRepository, VagonRepository vagonRepository){
        this.biletRepository = biletRepository;
        this.garaRepository = garaRepository;
        this.orarGaraRepository = orarGaraRepository;
        this.trenRepository = trenRepository;
        this.vagonRepository = vagonRepository;

        this.initBileteGrid();
        this.setBileteGrid(BiletMapper.biletEntityListToDTOs(biletRepository.findAll()));

        this.initSelects();
        this.setGaraSelects();
        this.setTrenSelect();
    }

    public void setI_biletView_and_init_gridView_and_selects(I_BiletView i_biletView){
        this.i_biletView = i_biletView;
        this.i_biletView.setGridView(bileteGrid);
        this.i_biletView.setGaraSelects(garaDePlecareSelect, garaDeDestinatieSelect);
        this.i_biletView.setOraDePlecareSiSosireSelect(oraDePlecareSiSosireSelect);
        this.i_biletView.setVagonSelect(vagonSelect);
        this.i_biletView.setFilterTrenSelect(filterTrenSelect);
        this.i_biletView.setFilterGaraDeDestinatieSelect(filterGaraDeDestinatieSelect);
    }

    public void saveBilet(){
        Bilet bilet = this.biletAddCheck();
        if(bilet != null) {
            try {
                biletRepository.save(bilet);

                bilet.getVagon().setNrLocuriLibere(bilet.getVagon().getNrLocuriLibere() - 1);
                vagonRepository.save(bilet.getVagon());
                this.setVagonSelect();
                this.setNrLocTextField();

                i_biletView.setMessage("Success!", "Successfully added new train ticket!");

                this.setBileteGrid(BiletMapper.biletEntityListToDTOs(biletRepository.findAll()));
                this.i_biletView.setGridView(bileteGrid);
            }
            catch (Exception ex) {
                i_biletView.setMessage("Failure!", "Could not add train ticket!");
            }
        }
    }

    public void updateBilet(){
        Bilet bilet = this.biletUpdateCheck();
        if(bilet != null){
            biletRepository.save(bilet);
            i_biletView.setMessage("Success!", "Successfully updated ticket information!");

            this.setBileteGrid(BiletMapper.biletEntityListToDTOs(biletRepository.findAll()));
            this.i_biletView.setGridView(bileteGrid);
        }
    }

    public void deleteBilet(){
        Long id = i_biletView.getSelectedBiletId();
        if(id == null){
            i_biletView.setMessage("Failure!", "No ticket is selected!");
        }
        else {
            biletRepository.deleteById(id);
            i_biletView.setMessage("Success!", "Successfully deleted ticket!");

            this.setBileteGrid(BiletMapper.biletEntityListToDTOs(biletRepository.findAll()));
            this.i_biletView.setGridView(bileteGrid);
        }
    }

    private Bilet biletAddCheck(){
        if(i_biletView.getDataCalatoriei() == null){
            i_biletView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        LocalDate dataCalatoriei = i_biletView.getDataCalatoriei();

        if(i_biletView.getGaraDePlecare() == null){
            i_biletView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        GaraDTO garaDePlecare = i_biletView.getGaraDePlecare();

        if(i_biletView.getGaraDeDestinatie() == null){
            i_biletView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        GaraDTO garaDeDestinatie = i_biletView.getGaraDeDestinatie();

        if(i_biletView.getOraDePlecare() == null){
            i_biletView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        LocalTime oraDePlecare = i_biletView.getOraDePlecare();

        if(i_biletView.getOraDeSosire() == null){
            i_biletView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        LocalTime oraDeSosire = i_biletView.getOraDeSosire();

        if(i_biletView.getVagon() == null){
            i_biletView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        VagonDTO vagonDTO = i_biletView.getVagon();

        int nrLoc = i_biletView.getNrLoc();

        return new Bilet(null, dataCalatoriei, GaraMapper.garaDTOtoEntity(garaDePlecare), GaraMapper.garaDTOtoEntity(garaDeDestinatie),
                oraDePlecare, oraDeSosire, VagonMapper.vagonDTOToEntity(vagonDTO), nrLoc);
    }

    private Bilet biletUpdateCheck(){
        Long id;
        if(i_biletView.getSelectedBiletId() == null){
            i_biletView.setMessage("Failure!", "No ticket is selected!");
            return null;
        }
        else{
            id = i_biletView.getSelectedBiletId();
        }

        LocalDate dataCalatoriei;
        if(i_biletView.getDataCalatoriei() == null){
            dataCalatoriei = i_biletView.getSelectedDataCalatoriei();
        }
        else{
            dataCalatoriei = i_biletView.getDataCalatoriei();
        }

        GaraDTO garaDePlecare, garaDeDestinatie;
        LocalTime oraDePlecare, oraDeSosire;
        VagonDTO vagonDTO;
        int nrLoc;
        if(i_biletView.getGaraDePlecare() == null || i_biletView.getGaraDeDestinatie() == null
                || i_biletView.getOraDePlecare() == null || i_biletView.getOraDeSosire() == null
                || i_biletView.getVagon() == null){
            garaDePlecare = i_biletView.getSelectedGaraDePlecare();
            garaDeDestinatie = i_biletView.getSelectedGaraDeDestinatie();
            oraDePlecare = i_biletView.getSelectedOraDePlecare();
            oraDeSosire = i_biletView.getSelectedOraDeSosire();
            vagonDTO = i_biletView.getSelectedVagon();
            nrLoc = i_biletView.getSelectedNrLoc();
        }
        else{
            garaDePlecare = i_biletView.getGaraDePlecare();
            garaDeDestinatie = i_biletView.getGaraDeDestinatie();
            oraDePlecare = i_biletView.getOraDePlecare();
            oraDeSosire = i_biletView.getOraDeSosire();
            vagonDTO = i_biletView.getVagon();
            nrLoc = i_biletView.getNrLoc();
        }

        return new Bilet(id, dataCalatoriei, GaraMapper.garaDTOtoEntity(garaDePlecare), GaraMapper.garaDTOtoEntity(garaDeDestinatie),
                oraDePlecare, oraDeSosire, VagonMapper.vagonDTOToEntity(vagonDTO), nrLoc);
    }

    private void initBileteGrid(){
        bileteGrid.addColumn(BiletDTO::dataCalatoriei).setHeader("Travel Date");
        bileteGrid.addColumn(BiletDTO::garaDePlecare).setHeader("Departure Station");
        bileteGrid.addColumn(BiletDTO::garaDeDestinatie).setHeader("Destination Station");
        bileteGrid.addColumn(BiletDTO::oraDePlecare).setHeader("Departure Time");
        bileteGrid.addColumn(BiletDTO::oraDeSosire).setHeader("Arrival Time");
        bileteGrid.addColumn(BiletDTO::tren).setHeader("Train Number");
        bileteGrid.addColumn(BiletDTO::vagon).setHeader("Wagon Number");
        bileteGrid.addColumn(BiletDTO::nrLoc).setHeader("Seat Number");
    }

    private void setBileteGrid(List<BiletDTO> biletDTOs){
        bileteGrid.setItems(biletDTOs);
    }

    private void initSelects(){
        garaDePlecareSelect.setLabel("Departure Station");
        garaDeDestinatieSelect.setLabel("Destination Station");
        oraDePlecareSiSosireSelect.setLabel("Departure and Arrival Time");
        vagonSelect.setLabel("Wagon Number");
        filterTrenSelect.setLabel("Filter by Train Number");
        filterGaraDeDestinatieSelect.setLabel("Filter by Destination Station");
    }

    private void setGaraSelects(){
        List<GaraDTO> garaDTOs = GaraMapper.garaEntityListToDTOs(garaRepository.findAll());
        garaDePlecareSelect.setItems(garaDTOs);
        garaDeDestinatieSelect.setItems(garaDTOs);
        filterGaraDeDestinatieSelect.setItems(garaDTOs);
    }

    private void setTrenSelect(){
        List<TrenDTO> trenDTOs = TrenMapper.trenEntityListToDTOs(trenRepository.findAll());
        //trenDTOs.addFirst(null);
        filterTrenSelect.setItems(trenDTOs);
    }

    public void setOraDePlecareSiSosireSelect(){
        GaraDTO garaDePlecare = i_biletView.getGaraDePlecare(), garaDeDestinatie = i_biletView.getGaraDeDestinatie();

        if(garaDePlecare != null && garaDeDestinatie != null && !garaDePlecare.equals(garaDeDestinatie)){
            List<OrarGara> orarGaraDePlecare = orarGaraRepository.findAllByStatie(GaraMapper.garaDTOtoEntity(garaDePlecare));
            List<OrarGara> orarGaraDeDestinatie = orarGaraRepository.findAllByStatie(GaraMapper.garaDTOtoEntity(garaDeDestinatie));

            List<OreDTO> orePosibile = orarGaraDeDestinatie.stream()
                    .map(orarGara -> new OreDTO(null, orarGara.getOraDeSosire(), orarGara.getTraseu(), TrenMapper.trenEntityToDTO(orarGara.getTren())))
                    .toList();

            List<OreDTO> oreleDePlecareSiSosire = new java.util.ArrayList<>(orarGaraDePlecare.stream()
                    .filter(orarGara -> orePosibile.contains(new OreDTO(null, null, orarGara.getTraseu(), TrenMapper.trenEntityToDTO(orarGara.getTren()))))
                    .map(orarGara -> new OreDTO(orarGara.getOraDePlecare(),
                            orePosibile.get(orePosibile.indexOf(new OreDTO(null, null, orarGara.getTraseu(), TrenMapper.trenEntityToDTO(orarGara.getTren())))).oraDeSosire(),
                            orarGara.getTraseu(), TrenMapper.trenEntityToDTO(orarGara.getTren())))
                    .toList());

            oreleDePlecareSiSosire.removeIf(oreDTO -> oreDTO.oraDePlecare().isAfter(oreDTO.oraDeSosire()));

            oraDePlecareSiSosireSelect.setItems(oreleDePlecareSiSosire);
            this.i_biletView.setOraDePlecareSiSosireSelect(oraDePlecareSiSosireSelect);
        }
    }

    public void setTrenTextField(){
        OreDTO oreDTO = i_biletView.getOraDePlecareSiSosire();
        if(oreDTO != null){
            this.i_biletView.setTrenTextField(oreDTO.trenDTO().nrTren());
        }
    }

    public void setVagonSelect(){
        OreDTO oreDTO = i_biletView.getOraDePlecareSiSosire();
        if(oreDTO != null) {
            String nrTren = oreDTO.trenDTO().nrTren();

            Optional<Tren> currentTren = trenRepository.findByNrTren(nrTren);
            if (currentTren.isPresent()) {
                List<VagonDTO> vagonDTOs = VagonMapper.vagonEntityListToDTOs(vagonRepository.findAllByTren(currentTren.get()));
                vagonDTOs = vagonDTOs.stream()
                        .filter(vagonDTO -> vagonDTO.nrLocuriLibere() > 0)
                        .toList();
                vagonSelect.setItems(vagonDTOs);
                this.i_biletView.setVagonSelect(vagonSelect);
            } else {
                i_biletView.setMessage("Error!", "Couldn't fetch information about the train with this Number!");
            }
        }
    }

    public void setNrLocTextField(){
        VagonDTO vagonDTO = i_biletView.getVagon();
        if(vagonDTO != null){
            this.i_biletView.setNrLocIntegerField(vagonDTO.nrLocuriLibere());
        }
    }

    public void filterBilete(){
        
    }

    public void filterByDataCalatoriei(){
        LocalDate dataCalatoriei = i_biletView.getFilterDataCalatorieiDatePicker();

        List<BiletDTO> biletDTOs;
        if(dataCalatoriei == null){
            biletDTOs = BiletMapper.biletEntityListToDTOs(biletRepository.findAll());
        }
        else{
            biletDTOs = BiletMapper.biletEntityListToDTOs(biletRepository.findAllByDataCalatoriei(dataCalatoriei));
        }

        this.setBileteGrid(biletDTOs);

    }

    public void filterByTren(){
        Tren tren = TrenMapper.trenDTOToEntity(i_biletView.getFilterTrenSelect());

        List<BiletDTO> biletDTOs;
        if(tren == null){
            biletDTOs = BiletMapper.biletEntityListToDTOs(biletRepository.findAll());
        }
        else{
            biletDTOs = BiletMapper.biletEntityListToDTOs(biletRepository.findAllByVagonTren(tren));
        }

        this.setBileteGrid(biletDTOs);
    }

    public void filterByGaraDeDestinatie(){
        Gara garaDeDestinatie = GaraMapper.garaDTOtoEntity(i_biletView.getFilterGaraDeDestinatieSelect());

        List<BiletDTO> biletDTOs;
        if(garaDeDestinatie == null){
            biletDTOs = BiletMapper.biletEntityListToDTOs(biletRepository.findAll());
        }
        else{
            biletDTOs = BiletMapper.biletEntityListToDTOs(biletRepository.findAllByGaraDeDestinatie(garaDeDestinatie));
        }

        this.setBileteGrid(biletDTOs);
    }

}
