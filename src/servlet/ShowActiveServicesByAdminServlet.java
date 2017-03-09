package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.model.ActiveServiceStatus;
import classes.model.Service;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ShowActiveServicesByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession(true).getAttribute("userForChange");
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(userId)
                .withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(userId)
                .withRequestType("activeServicesDescriptions"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        List<Service> serviceList = serviceResponse.getServices();
        List <String> disconnectedTypeList=new ArrayList<String>();
        for(int i=0;i<activeServicesList.size();i++){
            if(activeServicesList.get(i).getNewStatus()!=null) {
                if (activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED)) {
                    disconnectedTypeList.add(serviceList.get(i).getType());
                }
            }
        }
        List<ActiveService> newActiveServiceList=new ArrayList<ActiveService>();
        List<Service> newServiceList=new ArrayList<Service>();
        for(int i=0;i<activeServicesList.size();i++){
            boolean isExist=false;
            for(int j=0;j<disconnectedTypeList.size();j++){
                if(activeServicesList.get(i).getNewStatus()!=null) {
                    if (!(activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))
                            && (serviceList.get(i).getType().equals(disconnectedTypeList.get(j)))) {
                        isExist = true;
                    }
                }
            }
            if(!isExist){
                newActiveServiceList.add(activeServicesList.get(i));
                newServiceList.add(serviceList.get(i));
            }
        }
        request.setAttribute("activeServiceDescription", newServiceList);
        request.setAttribute("activeServiceList", newActiveServiceList);
        request.getRequestDispatcher("/showActiveServicesByAdminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession(true).getAttribute("userForChange");
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(userId)
                .withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(userId)
                .withRequestType("activeServicesDescriptions"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        List<Service> serviceList = serviceResponse.getServices();
        List <String> disconnectedTypeList=new ArrayList<String>();
        for(int i=0;i<activeServicesList.size();i++){
            if(activeServicesList.get(i).getNewStatus()!=null) {
                if (activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED)) {
                    System.out.println("here");
                    disconnectedTypeList.add(serviceList.get(i).getType());
                }
            }
        }
        System.out.println(disconnectedTypeList.size());
        List<ActiveService> newActiveServiceList=new ArrayList<ActiveService>();
        List<Service> newServiceList=new ArrayList<Service>();
        for(int i=0;i<activeServicesList.size();i++){
            boolean isExist=false;
            for(int j=0;j<disconnectedTypeList.size();j++){
                if(activeServicesList.get(i).getNewStatus()!=null) {
                    if (!(activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))
                            && (serviceList.get(i).getType().equals(disconnectedTypeList.get(j)))) {
                        isExist = true;
                    }
                }
            }
            if(!isExist){
                newActiveServiceList.add(activeServicesList.get(i));
                newServiceList.add(serviceList.get(i));
            }
        }
        request.setAttribute("activeServiceDescription", newServiceList);
        request.setAttribute("activeServiceList", newActiveServiceList);
        request.getRequestDispatcher("/showActiveServicesByAdminPage.jsp").forward(request, response);
    }
}
