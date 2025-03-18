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
import ro.ps.proiect.presenter.gui_interfaces.I_OrarGaraView;
import ro.ps.proiect.presenter.dto.GaraDTO;
import ro.ps.proiect.presenter.dto.OrarGaraDTO;
import ro.ps.proiect.presenter.dto.mapper.GaraMapper;
import ro.ps.proiect.presenter.dto.mapper.OrarGaraMapper;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class OrarGaraPresenter {

    private final OrarGaraRepository orarGaraRepository;
    private final GaraRepository garaRepository;
    private final TrenRepository trenRepository;
    private String nrTren = "";
    private I_OrarGaraView i_orarGaraView;
    private final Grid<OrarGaraDTO> orarGaraGrid = new Grid<>();
    private final Select<GaraDTO> garaSelect = new Select<>();

    public OrarGaraPresenter(OrarGaraRepository orarGaraRepository, GaraRepository garaRepository, TrenRepository trenRepository){
        this.orarGaraRepository = orarGaraRepository;
        this.garaRepository = garaRepository;
        this.trenRepository = trenRepository;

        this.initOrarGaraGrid();

        this.initGaraSelect();
        this.setGaraSelect();
    }

    public void setI_orarGaraView_and_init_gridView_and_select(I_OrarGaraView i_orarGaraView, String nrTren){
        this.nrTren = nrTren;
        this.i_orarGaraView = i_orarGaraView;

        this.setOrarGaraGrid(nrTren);
        this.i_orarGaraView.setGridView(orarGaraGrid);
        this.i_orarGaraView.setGaraSelect(garaSelect);
    }

    public void saveOrarGara(){
        OrarGara orarGara = this.orarGaraAddCheck();
        if(orarGara != null) {
            try {
                orarGaraRepository.save(orarGara);
                i_orarGaraView.setMessage("Success!", "Successfully added new schedule record!");

                this.setOrarGaraGrid(nrTren);
                this.i_orarGaraView.setGridView(orarGaraGrid);
            }
            catch (Exception ex){
                i_orarGaraView.setMessage("Failure!", "Could not add schedule record (check if the Route Number is correct)!");
            }
        }
    }

    public void updateOrarGara(){
        OrarGara orarGara = this.orarGaraUpdateCheck();
        if(orarGara != null){
            try {
                orarGaraRepository.save(orarGara);
                i_orarGaraView.setMessage("Success!", "Successfully updated schedule record information!");

                this.setOrarGaraGrid(nrTren);
                this.i_orarGaraView.setGridView(orarGaraGrid);
            }
            catch (Exception ex) {
                i_orarGaraView.setMessage("Failure!", "Could not update schedule record!");
            }
        }
    }

    public void deleteOrarGara(){
        Long id = i_orarGaraView.getSelectedOrarGaraId();
        if(id == null){
            i_orarGaraView.setMessage("Failure!", "No schedule record is selected!");
        }
        else {
            orarGaraRepository.deleteById(id);
            i_orarGaraView.setMessage("Success!", "Successfully deleted schedule record!");

            this.setOrarGaraGrid(nrTren);
            this.i_orarGaraView.setGridView(orarGaraGrid);
        }
    }

    private OrarGara orarGaraAddCheck(){
        if(i_orarGaraView.getGara() == null){
            i_orarGaraView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        GaraDTO garaDTO = i_orarGaraView.getGara();

        if(i_orarGaraView.getOraDeSosire() == null){
            i_orarGaraView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        LocalTime oraDeSosire = i_orarGaraView.getOraDeSosire();

        if(i_orarGaraView.getOraDePlecare() == null){
            i_orarGaraView.setMessage("Incomplete information!", "Input fields cannot be empty!");
            return null;
        }
        LocalTime oraDePlecare = i_orarGaraView.getOraDePlecare();

        if(i_orarGaraView.getTraseu() == null){
            i_orarGaraView.setMessage("Incomplete information!", "Input fields cannot be empty!");
            return null;
        }
        Integer traseu = i_orarGaraView.getTraseu();

        Tren currentTren = trenRepository.findByNrTren(nrTren).orElse(null);

        return new OrarGara(null, currentTren, GaraMapper.garaDTOtoEntity(garaDTO), oraDePlecare, oraDeSosire, traseu);
    }

    private OrarGara orarGaraUpdateCheck(){
        Long id;
        if(i_orarGaraView.getSelectedOrarGaraId() == null){
            i_orarGaraView.setMessage("Failure!", "No schedule record is selected!");
            return null;
        }
        else{
            id = i_orarGaraView.getSelectedOrarGaraId();
        }

        GaraDTO garaDTO;
        if(i_orarGaraView.getGara() == null){
            garaDTO = i_orarGaraView.getSelectedGara();
        }
        else{
            garaDTO = i_orarGaraView.getGara();
        }

        LocalTime oraDeSosire;
        if(i_orarGaraView.getOraDeSosire() == null){
            oraDeSosire = i_orarGaraView.getSelectedOraDeSosire();
        }
        else{
            oraDeSosire = i_orarGaraView.getOraDeSosire();
        }

        LocalTime oraDePlecare;
        if(i_orarGaraView.getOraDePlecare() == null){
            oraDePlecare = i_orarGaraView.getSelectedOraDePlecare();
        }
        else{
            oraDePlecare = i_orarGaraView.getOraDePlecare();
        }

        Integer traseu;
        if(i_orarGaraView.getTraseu() == null){
            traseu = i_orarGaraView.getSelectedTraseu();
        }
        else{
            traseu = i_orarGaraView.getTraseu();
        }

        Tren currentTren = trenRepository.findByNrTren(nrTren).orElse(null);

        return new OrarGara(id, currentTren, GaraMapper.garaDTOtoEntity(garaDTO), oraDePlecare, oraDeSosire, traseu);
    }

    private void redirectTrenView(){
        UI.getCurrent().navigate("trains");
    }

    private void initOrarGaraGrid(){
        orarGaraGrid.addColumn(OrarGaraDTO::garaDTO).setHeader("Train Station");
        orarGaraGrid.addColumn(OrarGaraDTO::oraDeSosire).setHeader("Arrival Time");
        orarGaraGrid.addColumn(OrarGaraDTO::oraDePlecare).setHeader("Departure Time");
        orarGaraGrid.addColumn(OrarGaraDTO::traseu).setHeader("Route Number");
    }

    private void setOrarGaraGrid(String nrTren){
        Optional<Tren> currentTren = trenRepository.findByNrTren(nrTren);
        if(currentTren.isPresent()){
            List<OrarGaraDTO> orarGaraDTOs = OrarGaraMapper.orarGaraEntityListToDTOs(orarGaraRepository.findAllByTren(currentTren.get()));
            orarGaraGrid.setItems(orarGaraDTOs);
        }
        else{
            i_orarGaraView.setMessage("Error!", "Couldn't fetch information about the train with this ID!");
            this.redirectTrenView();
        }
    }

    private void initGaraSelect(){
        garaSelect.setLabel("Train Station");
    }

    private void setGaraSelect(){
        List<GaraDTO> garaDTOs = GaraMapper.garaEntityListToDTOs(garaRepository.findAll());
        garaSelect.setItems(garaDTOs);
    }
}
