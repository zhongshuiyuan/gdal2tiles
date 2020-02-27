package com.walkgis.tiles.web.ctrl;

import com.walkgis.tiles.MainApp;
import com.walkgis.tiles.entity.ZoomComboboxModel;
import com.walkgis.tiles.util.GDAL2Tiles;
import com.walkgis.tiles.util.OptionObj;
import com.walkgis.tiles.web.sub.AdvanceSettingViewController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.walkgis.tiles.web.MainViewController.nextView;

public class PanelTileSettingController implements Initializable {
    @FXML
    private HBox panelOutputDir, panelOutputMBTiles, panelOutputGeopackage;
    @FXML
    private ComboBox cmbZoomFrom, cmbZoomTo;
    @FXML
    private Button btnSettingAdvance;
    private GDAL2Tiles gdal2TilesTemp;

    public ZoomComboboxModel zoomComboboxModel = new ZoomComboboxModel();

    private OptionObj optionObj = new OptionObj();

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.panelOutputDir.setOnMouseClicked(this::panelOutputDirClick);
        this.panelOutputMBTiles.setOnMouseClicked(this::panelOutputMBTilesClick);
        this.panelOutputGeopackage.setOnMouseClicked(this::panelOutputGeopackageClick);

        this.btnSettingAdvance.setOnMouseClicked(this::btnSettingAdvanceClick);

        this.cmbZoomFrom.setItems(zoomComboboxModel.getZoomFrom());
        this.cmbZoomFrom.valueProperty().bind(zoomComboboxModel.getZoomFromValue());

        this.cmbZoomTo.setItems(zoomComboboxModel.getZoomTo());
        this.cmbZoomTo.valueProperty().bind(zoomComboboxModel.getZoomToValue());
    }

    /**
     * 初始化 这里要实现打开这一步
     */
    public void init(File inputFile) {
        gdal2TilesTemp = new GDAL2Tiles();
        gdal2TilesTemp.setInput_file(inputFile.getAbsolutePath());
        optionObj.nb_processes = 1;
        try {
            gdal2TilesTemp.setOptions(optionObj);
            gdal2TilesTemp.open_input();
            zoomComboboxModel.setZoomFromValue(new SimpleIntegerProperty(gdal2TilesTemp.getTminz()));
            zoomComboboxModel.setZoomToValue(new SimpleIntegerProperty(gdal2TilesTemp.getTmaxz()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btnSettingAdvanceClick(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("advancesetting.fxml"));
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            AdvanceSettingViewController controller = fxmlLoader.getController();
            controller.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void panelOutputDirClick(MouseEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择保存的位置");
        Stage selectFile = new Stage();
        if ((MainApp.defaultDir == null || "".equals(MainApp.defaultDir)))
            MainApp.defaultDir = System.getProperty("user.home");
        directoryChooser.setInitialDirectory(new File(MainApp.defaultDir));

        File file = directoryChooser.showDialog(selectFile);
        if (file != null) {
            gdal2TilesTemp.setOutput_folder(file.getAbsolutePath());

            PanelProgressController panelProgressController;
            Object o = nextView("panelProgress");
            if (o != null) {
                panelProgressController = (PanelProgressController) o;
                panelProgressController.init(gdal2TilesTemp);
            }
        }
    }

    @FXML
    private void panelOutputMBTilesClick(MouseEvent event) {

    }

    @FXML
    private void panelOutputGeopackageClick(MouseEvent event) {

    }

}