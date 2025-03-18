package ro.ps.proiect.presenter.presenter;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.select.Select;
import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.OrarGara;
import ro.ps.proiect.model.data_structures.Tren;
import ro.ps.proiect.model.repository.GaraRepository;
import ro.ps.proiect.model.repository.OrarGaraRepository;
import ro.ps.proiect.model.repository.TrenRepository;
import ro.ps.proiect.presenter.gui_interfaces.I_TrenView;
import ro.ps.proiect.presenter.dto.TrenDTO;
import ro.ps.proiect.presenter.dto.GaraDTO;
import ro.ps.proiect.presenter.dto.mapper.GaraMapper;
import ro.ps.proiect.presenter.dto.mapper.TrenMapper;

import java.util.List;

@Component
public class TrenPresenter {

    private final TrenRepository trenRepository;
    private final GaraRepository garaRepository;
    private final OrarGaraRepository orarGaraRepository;
    private I_TrenView i_trenView;
    private final Grid<TrenDTO> trenuriGrid = new Grid<>();
    private final Select<GaraDTO> garaDePlecareSelect = new Select<>();
    private final Select<GaraDTO> garaDeSosireSelect = new Select<>();

    public TrenPresenter(TrenRepository trenRepository, GaraRepository garaRepository, OrarGaraRepository orarGaraRepository){
        this.trenRepository = trenRepository;
        this.garaRepository = garaRepository;
        this.orarGaraRepository = orarGaraRepository;

        this.initTrenuriGrid();
        this.setTrenuriGrid();

        this.initGaraSelects();
        this.setGaraSelects();
    }

    public void setI_trenView_and_init_gridView_and_selects(I_TrenView i_trenView){
        this.i_trenView = i_trenView;
        this.i_trenView.setGridView(trenuriGrid);
        this.i_trenView.setGaraSelects(garaDePlecareSelect, garaDeSosireSelect);
    }

    public void saveTren(){
        Tren tren = this.trenAddCheck();
        if(tren != null) {
            try {
                trenRepository.save(tren);
                orarGaraRepository.save(new OrarGara(null, tren, tren.getGaraDeSosire(), null, null, null));
                orarGaraRepository.save(new OrarGara(null, tren, tren.getGaraDePlecare(), null, null, null));
                i_trenView.setMessage("Success!", "Successfully added new train!");

                this.setTrenuriGrid();
                this.i_trenView.setGridView(trenuriGrid);
            }
            catch (Exception ex) {
                i_trenView.setMessage("Failure!", "A train with this number already exists!");
            }
        }
    }

    public void updateTren(){
        Tren tren = this.trenUpdateCheck();
        if(tren != null){
            try {
                trenRepository.save(tren);
                i_trenView.setMessage("Success!", "Successfully updated train information!");

                this.setTrenuriGrid();
                this.i_trenView.setGridView(trenuriGrid);
            }
            catch (Exception ex) {
                i_trenView.setMessage("Failure!", "A train with this number already exists!");
            }
        }
    }

    public void deleteTren(){
        Long id = i_trenView.getSelectedTrenId();
        if(id == null){
            i_trenView.setMessage("Failure!", "No train is selected!");
        }
        else {
            try {
                trenRepository.deleteById(id);
                i_trenView.setMessage("Success!", "Successfully deleted train!");

                this.setTrenuriGrid();
                this.i_trenView.setGridView(trenuriGrid);
            }
            catch (Exception ex) {
                i_trenView.setMessage("Failure!", "There are other records depending on information about this train!");
            }
        }
    }

    private Tren trenAddCheck(){
        if(i_trenView.getNrTren().isEmpty()){
            i_trenView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        String nrTren = i_trenView.getNrTren();

        if(i_trenView.getGaraDePlecare() == null){
            i_trenView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        GaraDTO garaDePlecare = i_trenView.getGaraDePlecare();

        if(i_trenView.getGaraDeSosire() == null){
            i_trenView.setMessage("Incomplete information!", "Input fields cannot be empty!");
            return null;
        }
        GaraDTO garaDeSosire = i_trenView.getGaraDeSosire();

        return new Tren(null, nrTren, GaraMapper.garaDTOtoEntity(garaDePlecare), GaraMapper.garaDTOtoEntity(garaDeSosire));
    }

    private Tren trenUpdateCheck(){
        Long id;
        if(i_trenView.getSelectedTrenId() == null){
            i_trenView.setMessage("Failure!", "No train is selected!");
            return null;
        }
        else{
            id = i_trenView.getSelectedTrenId();
        }

        String nrTren;
        if(i_trenView.getNrTren().isEmpty()){
            nrTren = i_trenView.getSelectedNrTren();
        }
        else{
            nrTren = i_trenView.getNrTren();
        }

        GaraDTO garaDePlecare;
        if(i_trenView.getGaraDePlecare() == null){
            garaDePlecare = i_trenView.getSelectedGaraDePlecare();
        }
        else{
            garaDePlecare = i_trenView.getGaraDePlecare();
        }

        GaraDTO garaDeSosire;
        if(i_trenView.getGaraDeSosire() == null){
            garaDeSosire = i_trenView.getSelectedGaraDeSosire();
        }
        else{
            garaDeSosire = i_trenView.getGaraDeSosire();
        }

        return new Tren(id, nrTren, GaraMapper.garaDTOtoEntity(garaDePlecare), GaraMapper.garaDTOtoEntity(garaDeSosire));
    }

    public void searchByNrTren(){
        if(i_trenView.getSearchNrTren().isEmpty()){
            setTrenuriGrid();
        }
        else{
            List<TrenDTO> trenDTOs = TrenMapper.trenEntityListToDTOs(trenRepository.findAllByNrTren(i_trenView.getSearchNrTren()));
            trenuriGrid.setItems(trenDTOs);
        }
    }

    public void redirectVagonView(){
        if(i_trenView.getSelectedTrenId() == null){
            i_trenView.setMessage("Failure!", "No train is selected!");
        }
        else{
            UI.getCurrent().navigate("wagons/" + i_trenView.getSelectedNrTren());
        }
    }

    public void redirectOrarGaraView(){
        if(i_trenView.getSelectedTrenId() == null){
            i_trenView.setMessage("Failure!", "No train is selected!");
        }
        else{
            UI.getCurrent().navigate("schedule/" + i_trenView.getSelectedNrTren());
        }
    }

    private void initTrenuriGrid(){
        trenuriGrid.addColumn(TrenDTO::nrTren).setHeader("Train Number").setSortable(true);
        trenuriGrid.addColumn(TrenDTO::garaDePlecare).setHeader("Departure Station");
        trenuriGrid.addColumn(TrenDTO::garaDeSosire).setHeader("Terminal Station");
    }

    private void setTrenuriGrid(){
        List<TrenDTO> trenDTOs = TrenMapper.trenEntityListToDTOs(trenRepository.findAll());
        trenuriGrid.setItems(trenDTOs);
    }

    private void initGaraSelects(){
        garaDePlecareSelect.setLabel("Terminal Station");
        garaDeSosireSelect.setLabel("Arrival Station");
    }

    private void setGaraSelects(){
        List<GaraDTO> garaDTOs = GaraMapper.garaEntityListToDTOs(garaRepository.findAll());
        garaDePlecareSelect.setItems(garaDTOs);
        garaDeSosireSelect.setItems(garaDTOs);
    }
}
