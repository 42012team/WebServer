package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.User;
import classes.model.UserParams;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;
import classes.response.impl.UserResponse;

import java.io.Serializable;
import java.util.List;

public class SearchUsersProcessor implements RequestProcessor, Serializable {

    Initializer initializer;

    public SearchUsersProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedUserParams userRequestParams = (TransmittedUserParams) request;
            System.out.println("Поиск пользователей с параметрами " + "name:" + userRequestParams.getName()
                    + " surname:" + userRequestParams.getSurname() + " email:" + userRequestParams.getEmail()
                    + " phone:" + userRequestParams.getPhone() + " address:" + userRequestParams.getAddress()
                    + " login:" + userRequestParams.getLogin() + " password:" + userRequestParams.getPassword());
            List<User> usersList = initializer.getUserManager().searchUsersByParams(UserParams.create()
                    .withName(userRequestParams.getName())
                    .withSurname(userRequestParams.getSurname())
                    .withEmail(userRequestParams.getEmail())
                    .withPhone(userRequestParams.getPhone())
                    .withAdress(userRequestParams.getAddress())
                    .withLogin(userRequestParams.getLogin())
                    .withPassword(userRequestParams.getPassword()));
            return UserResponse.create()
                    .withAllUsers(usersList)
                    .withResponseType("allUsers");
        } catch (Exception ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
    }

}
