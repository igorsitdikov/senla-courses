import com.senla.hotel.AppContext;
import com.senla.hotel.Application;
import com.senla.hotel.controller.AttendanceController;
import com.senla.hotel.controller.HotelController;
import com.senla.hotel.controller.ResidentController;
import com.senla.hotel.controller.RoomController;
import com.senla.hotel.repository.ResidentRepository;
import com.senla.hotel.repository.RoomHistoryRepository;
import com.senla.hotel.repository.RoomRepository;
import com.senla.hotel.repository.interfaces.IResidentRepository;
import com.senla.hotel.repository.interfaces.IRoomHistoryRepository;
import com.senla.hotel.repository.interfaces.IRoomRepository;
import com.senla.hotel.service.AttendanceService;
import com.senla.hotel.service.HotelAdminService;
import com.senla.hotel.service.ResidentService;
import com.senla.hotel.service.RoomHistoryService;
import com.senla.hotel.service.RoomService;
import com.senla.hotel.service.interfaces.IAttendanceService;
import com.senla.hotel.service.interfaces.IHotelAdminService;
import com.senla.hotel.service.interfaces.IResidentService;
import com.senla.hotel.service.interfaces.IRoomHistoryService;
import com.senla.hotel.service.interfaces.IRoomService;
import com.senla.hotel.utils.ParseUtils;
import com.senla.hotel.utils.PrinterUtils;
import com.senla.hotel.utils.SerializationUtils;
import com.senla.hotel.utils.csv.interfaces.ICsvReader;
import com.senla.hotel.utils.csv.interfaces.ICsvWriter;
import com.senla.hotel.utils.csv.reader.CsvReader;
import com.senla.hotel.utils.csv.writer.CsvWriter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args)
        throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Map<Class, Class> classMap = new HashMap<>();
        classMap.put(ICsvReader.class, CsvReader.class);
        classMap.put(ICsvWriter.class, CsvWriter.class);
        classMap.put(IResidentRepository.class, ResidentRepository.class);
        classMap.put(IRoomHistoryRepository.class, RoomHistoryRepository.class);
        classMap.put(IRoomRepository.class, RoomRepository.class);
        classMap.put(ParseUtils.class, ParseUtils.class);
        classMap.put(SerializationUtils.class, SerializationUtils.class);
        classMap.put(IAttendanceService.class, AttendanceService.class);
        classMap.put(IHotelAdminService.class, HotelAdminService.class);
        classMap.put(IResidentService.class, ResidentService.class);
        classMap.put(IRoomHistoryService.class, RoomHistoryService.class);
        classMap.put(IRoomService.class, RoomService.class);
        classMap.put(AttendanceController.class, AttendanceController.class);
        classMap.put(HotelController.class, HotelController.class);
        classMap.put(ResidentController.class, ResidentController.class);
        classMap.put(RoomController.class, RoomController.class);

        AppContext context = Application.run("com.senla.hotel", classMap);
//        SerializationUtils serializationUtils = context.getObject(SerializationUtils.class);
        AttendanceController attendanceController = context.getObject(AttendanceController.class);
        ResidentController residentController = context.getObject(ResidentController.class);
        HotelController hotelController = context.getObject(HotelController.class);
        RoomController roomController = context.getObject(RoomController.class);
//        serializationUtils.deserialize();
        hotelController.importData();
        PrinterUtils.show("Show all attendances");
        PrinterUtils.show(attendanceController.showAttendances());
        PrinterUtils.show(residentController.showResidents());
        PrinterUtils.show(roomController.showAllRooms());
//        Injector.test("com.senla.hotel.service");
//        Set<Class<?>> d = Injector.getClasses("com.senla.hotel");
//        Map<Class<?>, Object> instances = new LinkedHashMap<>();
//
//        d.forEach(cls -> {
//
//            for (Annotation annotation : cls.getAnnotations()) {
//                if (annotation.annotationType().equals(Singleton.class)) {
//                    System.out.println(cls.getName());
//                    try {
//                        instances.put(cls, cls.getMethod("getInstance").invoke(null));
//                    } catch (IllegalAccessException e) {
//                        System.err.println("Illegal Access!" + e);
//                    } catch (InvocationTargetException e) {
//                        System.err.println("Invocation Target!" + e);
//                    } catch (NoSuchMethodException e) {
//                        System.err.println("No Such Method! %s" + e);
//                    }
//                }
//            }
//        });
//        SerializationUtils.deserialize();
//        AttendanceService attendanceService = (AttendanceService) instances.get(AttendanceService.class);
//        Printer.show(attendanceService.showAttendances());
        System.out.println();
    }
}
