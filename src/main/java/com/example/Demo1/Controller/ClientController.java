package com.example.Demo1.Controller;


import com.example.Demo1.Model.Response.EntityResponse;
import com.example.Demo1.Model.SaveRequest.SaveClientRequest;
import com.example.Demo1.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/firstapi")
    public String firstapi() {
        return "its working";
    }

    @PostMapping("/saveOrUpdateClient")
    public ResponseEntity<?> saveOrUpdateClient(@RequestBody SaveClientRequest saveClientRequest) {
        try {
            return new ResponseEntity<>(new EntityResponse(clientService.saveOrUpdateClient(saveClientRequest), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }

    @GetMapping("/getByIdClient")
    public ResponseEntity<?> getByIdClient(@RequestParam Long clientId) {
        try {
            return new ResponseEntity<>(new EntityResponse(clientService.getById(clientId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "getAllClient", method = RequestMethod.GET)
    private ResponseEntity<?> getAllClient(@RequestParam(defaultValue = "0",required = false) Integer pageNo,
                                           @RequestParam(defaultValue = "30",required = false)Integer pageSize,
                                           /*@RequestParam(value = "name",required = false)String clientName,
                                           @RequestParam(required = false)String clientEmail,
                                           @RequestParam(required = false)String clientMobileNo,*/
                                           @RequestParam(required = false)String startdate,
                                           @RequestParam(required = false)String enddate) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            return new ResponseEntity<>(new EntityResponse(clientService.getAllByDeleted(startdate,enddate,pageable), 0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }

    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteById(@RequestParam Long clientId) {
        try {
            return new ResponseEntity<>(new EntityResponse(clientService.deleteById(clientId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @PutMapping("/changeStatus")
    public ResponseEntity<?> changeStatus(@RequestParam Long clientId) {
        try {
            return new ResponseEntity<>(new EntityResponse(clientService.changeStatus(clientId), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }

    }

    @PostMapping("/changepassword")
    public  ResponseEntity<?>password(@RequestParam(required =false)Long clientId,
                                      @RequestParam(required = false)String oldpassword,
                                      @RequestParam(required = false)String newpassword){
        try {
            return  new ResponseEntity<>(new EntityResponse(clientService.changepassword(clientId,oldpassword,newpassword),0),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
}
