package controller;

import com.vaka.daily.Application;
import com.vaka.daily.exception.ValidationException;
import com.vaka.daily.repository.ScheduleRepository;
import com.vaka.daily.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@Slf4j
@Transactional
public class UserControllerTest {
    private final static String TEST_URL = "/api/user";

    @Autowired
    MockMvc mockMvc;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @BeforeEach
    void setUp() {
        entityManager
                .createNativeQuery("alter sequence daily_user_user_id_seq restart with " + getNewId())
                .executeUpdate();

        entityManager
                .createNativeQuery("alter sequence schedule_schedule_id_seq restart with " + (scheduleRepository.count() + 1))
                .executeUpdate();
    }

    Integer getNewId() {
        return Math.toIntExact(userRepository.count() + 1);
    }

    @DisplayName("Should return all user")
    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(TEST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));
    }

    @DisplayName("Should return user with ID 1")
    @Test
    void testGetById() throws Exception {
        Integer ID = 1;
        mockMvc.perform(get(TEST_URL + "/{id}", ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.login").value("vaka"));

    }

    @DisplayName("Should create new user (all fields)")
    @Test
    void testPost() throws Exception {
        String jsonString = "{\"login\":\"new_user\",\"password\":\"new_password\",\"firstName\":\"Иван\"," +
                "\"secondName\":\"Новгородов\",\"patronymic\":\"Андреевич\",\"userType\":{\"id\":4,\"name\":\"developer\"},\"schedules\":[{\"id\":1,\"name\":\"main\",\"tasks\":[{\"id\":1,\"name\":\"Прочитать книгу\",\"description\":\"Прочитать книгу Java Core\",\"deadline\":\"2023-11-30T00:00:00\",\"status\":true},{\"id\":2,\"name\":\"Разработать REST API\",\"description\":\"Полностью сделать REST API Vaka Daily\",\"deadline\":\"2024-08-31T00:00:00\",\"status\":false},{\"id\":3,\"name\":\"Прочитать книгу\",\"description\":\"Закончить книгу Pro Spring 6\",\"deadline\":\"2024-07-31T00:00:00\",\"status\":false}]}]}\n";
        int newId = getNewId();

        mockMvc.perform(post(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newId))
                .andExpect(jsonPath("$.login").value("new_user"))
                .andExpect(jsonPath("$.password").value("new_password"));

    }

    @DisplayName("Should create new user (some fields)")
    @Test
    void testPost2() throws Exception {
        String jsonString = "{\"login\":\"new_user\",\"password\":\"new_password\",\"firstName\":\"Иван\"," +
                "\"secondName\":\"Новгородов\",\"patronymic\":\"Андреевич\"}";
        int newId = getNewId();

        mockMvc.perform(post(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(newId))
                .andExpect(jsonPath("$.login").value("new_user"))
                .andExpect(jsonPath("$.password").value("new_password"));

    }

    @DisplayName("Validation should failed (empty login)")
    @Test
    void testPost3() throws Exception {
        String jsonString = "{\"login\":\"\",\"password\":\"new_password\",\"firstName\":\"Иван\"," +
                "\"secondName\":\"Новгородов\",\"patronymic\":\"Андреевич\"}";

        mockMvc.perform(post(TEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @DisplayName("Should update user")
    @Test
    void testPut() throws Exception {
        String jsonString = "{\"login\":\"new_user\",\"password\":\"new_password\",\"firstName\":\"Иван\"," +
                "\"secondName\":\"Новгородов\",\"patronymic\":\"Андреевич\",\"userType\":{\"id\":4,\"name\":\"developer\"},\"schedules\":[{\"id\":1,\"name\":\"main\",\"tasks\":[{\"id\":1,\"name\":\"Прочитать книгу\",\"description\":\"Прочитать книгу Java Core\",\"deadline\":\"2023-11-30T00:00:00\",\"status\":true},{\"id\":2,\"name\":\"Разработать REST API\",\"description\":\"Полностью сделать REST API Vaka Daily\",\"deadline\":\"2024-08-31T00:00:00\",\"status\":false},{\"id\":3,\"name\":\"Прочитать книгу\",\"description\":\"Закончить книгу Pro Spring 6\",\"deadline\":\"2024-07-31T00:00:00\",\"status\":false}]}]}\n";
        Integer ID = 1;

        mockMvc.perform(put(TEST_URL + "/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.login").value("new_user"))
                .andExpect(jsonPath("$.password").value("new_password"))
                .andExpect(jsonPath("$.schedules[0].name").value("main"));
    }

    @DisplayName("Should delete user")
    @Test
    void testDelete() throws Exception {
        Integer ID = 1;

        mockMvc.perform(delete(TEST_URL + "/{id}", ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}