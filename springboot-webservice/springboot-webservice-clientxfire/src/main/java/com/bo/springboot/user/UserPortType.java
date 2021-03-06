
package com.bo.springboot.user;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "userPortType", targetNamespace = "http://www.youdomain.com/webservice")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UserPortType {


    /**
     * 
     * @param user
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "update", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.Update")
    @ResponseWrapper(localName = "updateResponse", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.UpdateResponse")
    public int update(
        @WebParam(name = "user", targetNamespace = "")
        UserDto user);

    /**
     * 
     * @param user
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "save", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.Save")
    @ResponseWrapper(localName = "saveResponse", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.SaveResponse")
    public int save(
        @WebParam(name = "user", targetNamespace = "")
        UserDto user);

    /**
     * 
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "count", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.Count")
    @ResponseWrapper(localName = "countResponse", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.CountResponse")
    public int count();

    /**
     * 
     * @param id
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "remove", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.Remove")
    @ResponseWrapper(localName = "removeResponse", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.RemoveResponse")
    public int remove(
        @WebParam(name = "id", targetNamespace = "")
        Integer id);

    /**
     * 
     * @param arg0
     * @return
     *     returns com.bo.springboot.user.UserDto
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUserById", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.GetUserById")
    @ResponseWrapper(localName = "getUserByIdResponse", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.GetUserByIdResponse")
    public UserDto getUserById(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param ids
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "batchRemove", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.BatchRemove")
    @ResponseWrapper(localName = "batchRemoveResponse", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.BatchRemoveResponse")
    public int batchRemove(
        @WebParam(name = "ids", targetNamespace = "")
        List<Integer> ids);

    /**
     * 
     * @return
     *     returns java.util.List<com.bo.springboot.user.UserDto>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getUsers", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.GetUsers")
    @ResponseWrapper(localName = "getUsersResponse", targetNamespace = "http://www.youdomain.com/webservice", className = "com.bo.springboot.user.GetUsersResponse")
    public List<UserDto> getUsers();

}
