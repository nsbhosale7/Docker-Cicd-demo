package com.app.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.custome_exception.ResourseNotFountException;
import com.app.dao.VehicleModelRepository;
import com.app.dto.VehicleModelDto;
import com.app.pojos.VehicleModel;
import com.app.pojos.VehicleSpecification;
@Service
@Transactional
public class VehicleModelServiceImpl implements IVehicleModelService {

	@Autowired
	private VehicleModelRepository vehModelRepo;
	
	@Value("${file.upload.location}")
	private String location;
	
	@Override
	public VehicleModel addVehicleModelDetails(VehicleModelDto transientVehModel, MultipartFile imageFile) throws IllegalStateException, IOException {
		System.out.println(location);
		VehicleModel vehmodel=new VehicleModel();
		VehicleSpecification vehmodspec=new VehicleSpecification();
		vehmodspec.setEngineCC(transientVehModel.getEngineCC());
		vehmodspec.setEngineCylinderinfo(transientVehModel.getEngineCylinderinfo());
		vehmodspec.setSegment(transientVehModel.getSegment());
		vehmodspec.setTransmission(transientVehModel.getTransmission());
		vehmodspec.setSeatingCapacity(transientVehModel.getSeatingCapacity());
		vehmodspec.setMileage(transientVehModel.getMileage());
		vehmodspec.setFuelTankCapacity(transientVehModel.getFuelTankCapacity());
		
		vehmodel.setModelName(transientVehModel.getModelName());
		vehmodel.setQuantity(transientVehModel.getQuantity());
		vehmodel.setVehicleSpecification(vehmodspec);
		vehmodel.setBasePrice(transientVehModel.getBasePrice());
		imageFile.transferTo(new File(location, imageFile.getOriginalFilename()));
		vehmodel.setImageName(imageFile.getOriginalFilename());
		return vehModelRepo.save(vehmodel);
	}

	@Override
	public VehicleModel getVehicleModelDetails(Integer modelId) {
		return vehModelRepo.findById(modelId)
				.orElseThrow(() -> new ResourseNotFountException("Model with id " + modelId + " not found!!!!!!!!!"));
		
	}

	@Override
	public List<VehicleModel> getListOfAvailableModel() {
		
		return vehModelRepo.findAll();
	}

	@Override
	public String deleteVehicleModel(Integer id) {
		 vehModelRepo.deleteById(id);
		 return "Model deleted Successfully";
	}

	@Override
	public VehicleModel updateVehicleModel(VehicleModel vehicleModel) {
		
		
		return vehModelRepo.save(vehicleModel);
	}

	

	
}
