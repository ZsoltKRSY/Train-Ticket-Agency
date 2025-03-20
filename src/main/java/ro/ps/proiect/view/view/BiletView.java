package ro.ps.proiect.view.view;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;
import ro.ps.proiect.presenter.dto.*;
import ro.ps.proiect.presenter.gui_interfaces.I_BiletView;
import ro.ps.proiect.presenter.presenter.BiletPresenter;
import ro.ps.proiect.view.layout.MainLayout;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@PageTitle("Tickets")
@Route(value = "/tickets", layout = MainLayout.class)
@Component
@UIScope
public class BiletView extends VerticalLayout implements I_BiletView {
    private final BiletPresenter biletPresenter;

    private final DatePicker dataCalatorieiDatePicker = new DatePicker("Travel Date");
    private Select<GaraDTO> garaDePlecareSelect = new Select<>();
    private Select<GaraDTO> garaDeDestinatieSelect = new Select<>();
    private Select<OreDTO> oraDePlecareSiSosireSelect = new Select<>();
    private TextField trenTextField = new TextField("Train Number");
    private Select<VagonDTO> vagonSelect = new Select<>();
    private IntegerField nrLocIntegerField = new IntegerField("Seat Number");
    private final Button addBiletButton;
    private final Button updateBiletButton;
    private final Button deleteBiletButton;
    private Grid<BiletDTO> bileteGrid = new Grid<>();
    private final Dialog dialogBox = new Dialog();
    private final NativeLabel dialogMessage = new NativeLabel();
    private final Button okButton;

    private Select<TrenDTO> filterTrenSelect = new Select<>();
    private final DatePicker filterDataCalatorieiDatePicker = new DatePicker("Filter by Travel Date");
    private Select<GaraDTO> filterGaraDeDestinatieSelect = new Select<>();

    private final Select<String> salvareListaFormatSelect = new Select<>();
    private final Button salvareListaButton;
    private final Anchor salvareListaAnchor;

    public BiletView(BiletPresenter biletPresenter){
        this.biletPresenter = biletPresenter;
        biletPresenter.setI_biletView_and_init_gridView_and_selects(this);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(dialogMessage);
        dialogBox.add(dialogLayout);

        okButton = new Button("Ok", e -> dialogBox.close());
        dialogBox.getFooter().add(okButton);

        garaDePlecareSelect.addValueChangeListener(event -> setOreleDePlecareSiSosire());
        garaDeDestinatieSelect.addValueChangeListener(event -> setOreleDePlecareSiSosire());
        oraDePlecareSiSosireSelect.addValueChangeListener(event -> setTren());
        oraDePlecareSiSosireSelect.addValueChangeListener(event -> setVagoane());
        vagonSelect.addValueChangeListener(event -> setNrLoc());
        addBiletButton = new Button("Add", this::saveBilet);
        updateBiletButton = new Button("Update", this::updateBilet);
        deleteBiletButton = new Button("Delete", this::deleteBilet);

        salvareListaFormatSelect.setItems(".csv", ".doc");
        salvareListaFormatSelect.setValue(".csv");
        salvareListaAnchor = new Anchor();
        salvareListaButton = new Button("Save", this::salvareListaBilete);
        salvareListaAnchor.add(salvareListaButton);

        filterTrenSelect.setEmptySelectionAllowed(true);
        filterTrenSelect.setEmptySelectionCaption("None");
        filterGaraDeDestinatieSelect.setEmptySelectionAllowed(true);
        filterGaraDeDestinatieSelect.setEmptySelectionCaption("None");
        filterDataCalatorieiDatePicker.addValueChangeListener(event -> filterBilete());
        filterTrenSelect.addValueChangeListener(event -> filterBilete());
        filterGaraDeDestinatieSelect.addValueChangeListener(event -> filterBilete());

        trenTextField.setReadOnly(true);
        nrLocIntegerField.setReadOnly(true);

        this.addClassName("centered-content");

        VerticalLayout layout = new VerticalLayout();

        HorizontalLayout grid_and_filterLayout = new HorizontalLayout();
        VerticalLayout filterLayout = new VerticalLayout();
        filterLayout.add(filterDataCalatorieiDatePicker, filterTrenSelect, filterGaraDeDestinatieSelect);
        filterLayout.setWidth("300px");
        bileteGrid.addClassName("grid");

        grid_and_filterLayout.add(bileteGrid);
        grid_and_filterLayout.add(filterLayout);
        grid_and_filterLayout.addClassName("grid-filters");

        layout.add(grid_and_filterLayout);

        HorizontalLayout textFieldsLayout = new HorizontalLayout(dataCalatorieiDatePicker, garaDePlecareSelect, garaDeDestinatieSelect,
                oraDePlecareSiSosireSelect, trenTextField, vagonSelect, nrLocIntegerField);
        textFieldsLayout.addClassName("textfields");
        layout.add(textFieldsLayout);

        HorizontalLayout buttonsLayout = new HorizontalLayout(addBiletButton, updateBiletButton, deleteBiletButton,
                salvareListaFormatSelect, salvareListaAnchor);
        buttonsLayout.addClassName("buttons");
        addBiletButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        updateBiletButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        deleteBiletButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        layout.add(buttonsLayout);

        this.add(layout);
    }

    //event handling
    private void saveBilet(ClickEvent clickEvent){
        biletPresenter.saveBilet();
    }

    private void updateBilet(ClickEvent clickEvent){
        biletPresenter.updateBilet();
    }

    private void deleteBilet(ClickEvent clickEvent){
        biletPresenter.deleteBilet();
    }

    private void setOreleDePlecareSiSosire(){
        biletPresenter.setOraDePlecareSiSosireSelect();
    }

    private void setTren(){
        biletPresenter.setTrenTextField();
    }

    private void setVagoane(){
        biletPresenter.setVagonSelect();
    }

    private void setNrLoc(){
        biletPresenter.setNrLocTextField();
    }

    private void filterBilete(){
        biletPresenter.filterBilete();
    }

    private void salvareListaBilete(ClickEvent clickEvent){
        biletPresenter.salvareListaBilete();
    }

    //interface methods
    @Override
    public Long getSelectedBiletId() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::id).orElse(null);
    }

    @Override
    public LocalDate getDataCalatoriei() {
        return dataCalatorieiDatePicker.getValue();
    }

    @Override
    public LocalDate getSelectedDataCalatoriei() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::dataCalatoriei).orElse(null);
    }

    @Override
    public GaraDTO getGaraDePlecare() {
        return garaDePlecareSelect.getValue();
    }

    @Override
    public GaraDTO getSelectedGaraDePlecare() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::garaDePlecare).orElse(null);
    }

    @Override
    public GaraDTO getGaraDeDestinatie() {
        return garaDeDestinatieSelect.getValue();
    }

    @Override
    public GaraDTO getSelectedGaraDeDestinatie() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::garaDeDestinatie).orElse(null);
    }

    @Override
    public LocalTime getOraDePlecare() {
        return oraDePlecareSiSosireSelect.getValue().oraDePlecare();
    }

    @Override
    public LocalTime getSelectedOraDePlecare() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::oraDePlecare).orElse(null);
    }

    @Override
    public LocalTime getOraDeSosire() {
        return oraDePlecareSiSosireSelect.getValue().oraDeSosire();
    }

    @Override
    public LocalTime getSelectedOraDeSosire() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::oraDeSosire).orElse(null);
    }

    @Override
    public OreDTO getOraDePlecareSiSosire(){
        return oraDePlecareSiSosireSelect.getValue();
    }

    @Override
    public VagonDTO getVagon() {
        return vagonSelect.getValue();
    }

    @Override
    public VagonDTO getSelectedVagon() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::vagon).orElse(null);
    }

    @Override
    public int getNrLoc() {
        return nrLocIntegerField.getValue();
    }

    @Override
    public int getSelectedNrLoc() {
        Optional<BiletDTO> biletDTO = bileteGrid.getSelectedItems().stream().findFirst();
        return biletDTO.map(BiletDTO::nrLoc).orElse(1);
    }

    @Override
    public void setMessage(String title, String message) {
        dialogBox.setHeaderTitle(title);
        dialogMessage.removeAll();
        dialogMessage.add(message);

        dialogBox.open();
    }

    @Override
    public void setGridView(Grid<BiletDTO> grid) {
        bileteGrid = grid;
        bileteGrid.getDataProvider().refreshAll();
    }

    @Override
    public void setGaraSelects(Select<GaraDTO> garaDePlecareSelect, Select<GaraDTO> garaDeDestinatieSelect) {
        this.garaDePlecareSelect = garaDePlecareSelect;
        this.garaDePlecareSelect.getDataProvider().refreshAll();
        this.garaDeDestinatieSelect = garaDeDestinatieSelect;
        this.garaDeDestinatieSelect.getDataProvider().refreshAll();
    }

    @Override
    public void setOraDePlecareSiSosireSelect(Select<OreDTO> oraDePlecareSiSosireSelect){
        this.oraDePlecareSiSosireSelect = oraDePlecareSiSosireSelect;
        this.oraDePlecareSiSosireSelect.getDataProvider().refreshAll();
    }

    @Override
    public void setTrenTextField(String nrTren){
        this.trenTextField.setValue(nrTren);
    }

    @Override
    public void setVagonSelect(Select<VagonDTO> vagonSelect) {
        this.vagonSelect = vagonSelect;
        this.vagonSelect.getDataProvider().refreshAll();
    }

    @Override
    public void setNrLocIntegerField(int nrLoc) {
        this.nrLocIntegerField.setValue(nrLoc);
    }

    @Override
    public LocalDate getFilterDataCalatorieiDatePicker() {
        return filterDataCalatorieiDatePicker.getValue();
    }

    @Override
    public TrenDTO getFilterTrenSelect() {
        return filterTrenSelect.getValue();
    }

    @Override
    public GaraDTO getFilterGaraDeDestinatieSelect() {
        return filterGaraDeDestinatieSelect.getValue();
    }

    @Override
    public void setFilterTrenSelect(Select<TrenDTO> filterTrenSelect) {
        this.filterTrenSelect = filterTrenSelect;
        this.filterTrenSelect.getDataProvider().refreshAll();
    }

    @Override
    public void setFilterGaraDeDestinatieSelect(Select<GaraDTO> filterGaraDeDestinatieSelect) {
        this.filterGaraDeDestinatieSelect = filterGaraDeDestinatieSelect;
        this.filterGaraDeDestinatieSelect.getDataProvider().refreshAll();
    }

    @Override
    public String getSalvareListaFormatSelect(){
        return salvareListaFormatSelect.getValue();
    }

    @Override
    public void salvareListaBilete(StreamResource streamResource, String format) {
        salvareListaAnchor.setHref(streamResource);
        salvareListaAnchor.getElement().setAttribute("download", "bilete" + format);
        UI.getCurrent().getPage().executeJs("setTimeout(() => $0.click(), 100)", salvareListaAnchor.getElement());
    }

}
