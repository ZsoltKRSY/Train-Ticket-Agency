package ro.ps.proiect.presenter.presenter;

import com.vaadin.flow.component.grid.Grid;
import org.springframework.stereotype.Component;
import ro.ps.proiect.model.data_structures.Gara;
import ro.ps.proiect.model.repository.GaraRepository;
import ro.ps.proiect.presenter.gui_interfaces.I_GaraView;
import ro.ps.proiect.presenter.dto.mapper.GaraMapper;
import ro.ps.proiect.presenter.dto.GaraDTO;

import java.util.List;

@Component
public class GaraPresenter {

    private final GaraRepository garaRepository;
    private I_GaraView i_garaView;
    private final Grid<GaraDTO> gariGrid = new Grid<>();

    public GaraPresenter(GaraRepository garaRepository){
        this.garaRepository = garaRepository;

        this.initGariGrid();
        this.setGariGrid();
    }

    public void setI_garaView_and_init_gridView(I_GaraView i_garaView){
        this.i_garaView = i_garaView;
        this.i_garaView.setGridView(gariGrid);
    }

    public void saveGara(){
        Gara gara = this.garaAddCheck();
        if(gara != null) {
            garaRepository.save(gara);
            i_garaView.setMessage("Success!", "Successfully added new train station!");

            this.setGariGrid();
            this.i_garaView.setGridView(gariGrid);
        }
    }

    public void updateGara(){
        Gara gara = this.garaUpdateCheck();
        if(gara != null){
            garaRepository.save(gara);
            i_garaView.setMessage("Success!", "Successfully updated station information!");

            this.setGariGrid();
            this.i_garaView.setGridView(gariGrid);
        }
    }

    public void deleteGara(){
        Long id = i_garaView.getSelectedGaraId();
        if(id == null){
            i_garaView.setMessage("Failure!", "No train station is selected!");
        }
        else {
            garaRepository.deleteById(id);
            i_garaView.setMessage("Success!", "Successfully deleted station!");

            this.setGariGrid();
            this.i_garaView.setGridView(gariGrid);
        }
    }

    private Gara garaAddCheck(){
        if(i_garaView.getGaraJudet().isEmpty()){
            i_garaView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        String judet = i_garaView.getGaraJudet();

        if(i_garaView.getGaraLocalitate().isEmpty()){
            i_garaView.setMessage("Failure!", "Input fields cannot be empty!");
            return null;
        }
        String localitate = i_garaView.getGaraLocalitate();

        if(i_garaView.getGaraAdresaStrada().isEmpty()){
            i_garaView.setMessage("Incomplete information!", "Input fields cannot be empty!");
            return null;
        }
        String adresaStrada = i_garaView.getGaraAdresaStrada();

        return new Gara(null, judet, localitate, adresaStrada);
    }

    private Gara garaUpdateCheck(){
        Long id;
        if(i_garaView.getSelectedGaraId() == null){
            i_garaView.setMessage("Failure!", "No train station is selected!");
            return null;
        }
        else{
            id = i_garaView.getSelectedGaraId();
        }

        String judet;
        if(i_garaView.getGaraJudet().isEmpty()){
            judet = i_garaView.getSelectedGaraJudet();
        }
        else{
            judet = i_garaView.getGaraJudet();
        }

        String localitate;
        if(i_garaView.getGaraLocalitate().isEmpty()){
            localitate = i_garaView.getSelectedGaraLocalitate();
        }
        else{
            localitate = i_garaView.getGaraLocalitate();
        }

        String adresaStrada;
        if(i_garaView.getGaraAdresaStrada().isEmpty()){
            adresaStrada = i_garaView.getSelectedGaraAdresaStrada();
        }
        else{
            adresaStrada = i_garaView.getGaraAdresaStrada();
        }

        return new Gara(id, judet, localitate, adresaStrada);
    }

    private void initGariGrid(){
        gariGrid.addColumn(GaraDTO::judet).setHeader("County");
        gariGrid.addColumn(GaraDTO::localitate).setHeader("City");
        gariGrid.addColumn(GaraDTO::adresaStrada).setHeader("Street Address");
    }

    private void setGariGrid(){
        List<GaraDTO> gariDTOs = GaraMapper.garaEntityListToDTOs(garaRepository.findAll());
        gariGrid.setItems(gariDTOs);
    }
}
