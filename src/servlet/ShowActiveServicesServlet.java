package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.model.ActiveServiceStatus;
import classes.model.Service;
import classes.model.User;
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


public class ShowActiveServicesServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(true).getAttribute("user");
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(user.getId()).withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(user.getId()).withRequestType("activeServicesDescriptions"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        System.out.println("heere");
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        System.out.println("heere1");
        List<Service> serviceList = serviceResponse.getServices();
        List <String> disconnectedTypeList=new ArrayList<String>();
        System.out.println(disconnectedTypeList.size());
        for(int i=0;i<activeServicesList.size();i++){
            if(activeServicesList.get(i).getNewStatus()!=null) {
                if (activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED)) {
                    disconnectedTypeList.add(serviceList.get(i).getType());
                }
            }
        }
        System.out.println("mur");
        List<ActiveService> newActiveServiceList=new ArrayList<ActiveService>();
        List<Service> newServiceList=new ArrayList<Service>();
        for(int i=0;i<activeServicesList.size();i++){
            boolean isExist=false;
            System.out.println("lalala");
            for(int j=0;j<disconnectedTypeList.size();j++){
                if(activeServicesList.get(i).getNewStatus()!=null) {
                    if (!(activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))
                            && (serviceList.get(i).getType().equals(disconnectedTypeList.get(j)))) {
                        isExist = true;
                    }
                }
           }
            if(!isExist){
                System.out.println("add");
                newActiveServiceList.add(activeServicesList.get(i));
                newServiceList.add(serviceList.get(i));
            }
        }

        System.out.println(newActiveServiceList.size()+" "+newServiceList.size());
        request.setAttribute("activeServiceDescription", newServiceList);
        request.setAttribute("activeServiceList", newActiveServiceList);
        request.getRequestDispatcher("/showAllActiveServicesPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(true).getAttribute("user");
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(user.getId()).withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(user.getId()).withRequestType("activeServicesDescriptions"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        System.out.println("heere");
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        System.out.println("heere1");
        List<Service> serviceList = serviceResponse.getServices();
        List <String> disconnectedTypeList=new ArrayList<String>();
        System.out.println(disconnectedTypeList.size());
        for(int i=0;i<activeServicesList.size();i++){
            if(activeServicesList.get(i).getNewStatus()!=null) {
                if (activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED)) {
                    disconnectedTypeList.add(serviceList.get(i).getType());
                }
            }
        }
        System.out.println("mur");
        List<ActiveService> newActiveServiceList=new ArrayList<ActiveService>();
        List<Service> newServiceList=new ArrayList<Service>();
        for(int i=0;i<activeServicesList.size();i++){
            boolean isExist=false;
            System.out.println("lalala");
            for(int j=0;j<disconnectedTypeList.size();j++){
                if(activeServicesList.get(i).getNewStatus()!=null) {
                    if (!(activeServicesList.get(i).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))
                            && (serviceList.get(i).getType().equals(disconnectedTypeList.get(j)))) {
                        isExist = true;
                    }
                }
            }
            if(!isExist){
                System.out.println("add");
                newActiveServiceList.add(activeServicesList.get(i));
                newServiceList.add(serviceList.get(i));
            }
        }

        System.out.println(newActiveServiceList.size()+" "+newServiceList.size());
        request.setAttribute("activeServiceDescription", newServiceList);
        request.setAttribute("activeServiceList", newActiveServiceList);
        request.getRequestDispatcher("/showAllActiveServicesPage.jsp").forward(request, response);
    }


}
