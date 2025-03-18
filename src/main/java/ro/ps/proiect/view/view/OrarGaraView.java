package ro.ps.proiect.view.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import ro.ps.proiect.presenter.gui_interfaces.I_OrarGaraView;
import ro.ps.proiect.presenter.presenter.OrarGaraPresenter;
import ro.ps.proiect.presenter.dto.GaraDTO;
import ro.ps.proiect.presenter.dto.OrarGaraDTO;
import ro.ps.proiect.view.layout.MainLayout;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Optional;

@PageTitle("Schedule")
@Route(value = "schedule/", layout = MainLayout.class)
@Component
@UIScope
public class OrarGaraView extends VerticalLayout implements I_OrarGaraView, HasUrlParameter<String> {

    private final OrarGaraPresenter orarGaraPresenter;

    private String nrTren = "";
    private final NativeLabel idTrenLabel = new NativeLabel();
    private Select<GaraDTO> garaSelect = new Select<>();
    private TimePicker oraDeSosireTimePicker = new TimePicker("Arrival Time");
    private TimePicker oraDePlecareTimePicker = new TimePicker("Departure Time");
    private final IntegerField traseuIntegerField = new IntegerField("Route Number");
    private final Button addOrarGaraButton;
    private final Button updateOrarGaraButton;
    private final Button deleteOrarGaraButton;
    private Grid<OrarGaraDTO> orarGaraGrid = new Grid<>();
    private final Dialog dialogBox = new Dialog();
    private final NativeLabel dialogMessage = new NativeLabel();
    private final Button okButton;

    public OrarGaraView(OrarGaraPresenter orarGaraPresenter){
        this.orarGaraPresenter = orarGaraPresenter;

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(dialogMessage);
        dialogBox.add(dialogLayout);

        okButton = new Button("Ok", e -> dialogBox.close());
        dialogBox.getFooter().add(okButton);

        addOrarGaraButton = new Button("Add", this::saveOrarGara);
        updateOrarGaraButton = new Button("Update", this::updateOrarGara);
        deleteOrarGaraButton = new Button("Delete", this::deleteOrarGara);
    }

    private void initGUI(){
        this.removeAll();
        this.addClassName("centered-content");

        VerticalLayout layout = new VerticalLayout();

        idTrenLabel.removeAll();
        idTrenLabel.add("Schedule for train with Number " + nrTren);
        idTrenLabel.setClassName("title");
        layout.add(idTrenLabel);

        orarGaraGrid.addClassName("grid");
        layout.add(orarGaraGrid);

        oraDeSosireTimePicker.setStep(Duration.ofMinutes(1));
        oraDePlecareTimePicker.setStep(Duration.ofMinutes(1));
        HorizontalLayout textFieldsLayout = new HorizontalLayout(garaSelect, oraDeSosireTimePicker, oraDePlecareTimePicker, traseuIntegerField);
        textFieldsLayout.addClassName("textfields");
        layout.add(textFieldsLayout);

        HorizontalLayout buttonsLayout = new HorizontalLayout(addOrarGaraButton, updateOrarGaraButton, deleteOrarGaraButton);
        buttonsLayout.addClassName("buttons");
        addOrarGaraButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        updateOrarGaraButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        deleteOrarGaraButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        layout.add(buttonsLayout);

        this.add(layout);
    }

    private void saveOrarGara(ClickEvent clickEvent){
        orarGaraPresenter.saveOrarGara();
    }

    private void updateOrarGara(ClickEvent clickEvent){
        orarGaraPresenter.updateOrarGara();
    }

    private void deleteOrarGara(ClickEvent clickEvent){
        orarGaraPresenter.deleteOrarGara();
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, String nrTren) {
        if(nrTren == null){
            this.setMessage("Error!", "There was an error getting the Number of the selected train!");
        }
        else{
            this.nrTren = nrTren;
            orarGaraPresenter.setI_orarGaraView_and_init_gridView_and_select(this, nrTren);
            initGUI();
        }
    }

    @Override
    public Long getSelectedOrarGaraId() {
        Optional<OrarGaraDTO> orarGaraDTO = orarGaraGrid.getSelectedItems().stream().findFirst();
        return orarGaraDTO.map(OrarGaraDTO::id).orElse(null);
    }

    @Override
    public GaraDTO getGara() {
        return garaSelect.getValue();
    }

    @Override
    public GaraDTO getSelectedGara() {
        Optional<OrarGaraDTO> orarGaraDTO = orarGaraGrid.getSelectedItems().stream().findFirst();
        return orarGaraDTO.map(OrarGaraDTO::garaDTO).orElse(null);
    }

    @Override
    public LocalTime getOraDeSosire() {
        return oraDeSosireTimePicker.getValue();
    }

    @Override
    public LocalTime getSelectedOraDeSosire() {
        Optional<OrarGaraDTO> orarGaraDTO = orarGaraGrid.getSelectedItems().stream().findFirst();
        return orarGaraDTO.map(OrarGaraDTO::oraDeSosire).orElse(null);
    }

    @Override
    public LocalTime getOraDePlecare() {
        return oraDePlecareTimePicker.getValue();
    }

    @Override
    public LocalTime getSelectedOraDePlecare() {
        Optional<OrarGaraDTO> orarGaraDTO = orarGaraGrid.getSelectedItems().stream().findFirst();
        return orarGaraDTO.map(OrarGaraDTO::oraDePlecare).orElse(null);
    }

    @Override
    public Integer getTraseu(){
        return traseuIntegerField.getValue();
    }

    @Override
    public Integer getSelectedTraseu(){
        Optional<OrarGaraDTO> orarGaraDTO = orarGaraGrid.getSelectedItems().stream().findFirst();
        return orarGaraDTO.map(OrarGaraDTO::traseu).orElse(null);
    }

    @Override
    public void setMessage(String title, String message) {
        dialogBox.setHeaderTitle(title);
        dialogMessage.removeAll();
        dialogMessage.add(message);

        dialogBox.open();
    }

    @Override
    public void setGridView(Grid<OrarGaraDTO> grid) {
        orarGaraGrid = grid;
        orarGaraGrid.getDataProvider().refreshAll();
    }

    @Override
    public void setGaraSelect(Select<GaraDTO> garaSelect) {
        this.garaSelect = garaSelect;
        this.garaSelect.getDataProvider().refreshAll();
    }
}
