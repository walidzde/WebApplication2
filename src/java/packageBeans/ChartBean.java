package packageBeans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.InputStream;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.RequestScoped;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 *
 * @author Wafaa
 */
@ManagedBean
@RequestScoped
public class ChartBean {

    private BarChartModel barChartModel;

    public ChartBean() {
    }

    @PostConstruct
    public void init() {

        try {
            InputStream input = getClass().getResourceAsStream("newXMLDocument.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = dBuilder.parse(input);
            Element element = doc.getDocumentElement();
            NodeList listEtudiant = doc.getElementsByTagName("etudiant");
            barChartModel = new BarChartModel();
            for (int i = 0; i < listEtudiant.getLength(); i++) { // returns 3
                Node p = listEtudiant.item(i);
                Map<String, Integer> values = new HashMap<>();

                if (p.getNodeType() == Node.ELEMENT_NODE) {
                    Element etudiant = (Element) p;
                    NodeList list = etudiant.getChildNodes();
                    for (int j = 0; j < list.getLength(); j++) {
                        Node n = list.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element oneStudent = (Element) n;
                            if (values.containsKey(n.getNodeName())) {
                                Integer get = values.get(n.getNodeName());
                                get++;
                                values.put(n.getNodeName(), get);
                            } else {
                                values.put(n.getNodeName(), 1);
                            }
                        }
                    }
                }
                ChartSeries studentsData = new ChartSeries();
                int number = i + 1;
                studentsData.setLabel("etudiant" + number);
                values.forEach((k, v) -> studentsData.set(k, v));
                barChartModel.addSeries(studentsData);
                values = new HashMap<>();

            }
            barChartModel.setLegendPosition("ne");

        } catch (Exception e) {
            System.out.println("Exception was caught " + e);
        }

    }

    public BarChartModel getBarChartModel() {
        return barChartModel;
    }

    public void setBarChartModel(BarChartModel barChartModel) {
        this.barChartModel = barChartModel;
    }

}
