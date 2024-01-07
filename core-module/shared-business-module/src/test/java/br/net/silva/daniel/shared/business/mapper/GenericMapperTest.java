package br.net.silva.daniel.shared.business.mapper;

import br.net.silva.daniel.shared.business.exception.MapperNotConvertErrorException;
import junit.framework.TestCase;

public class GenericMapperTest extends TestCase {

    public void testConvertWithMap() {
        var person = new PersonDTO();
        person.setName("Test");
        person.setAge(25);

        GenericMapper<AnimalDTO> genericMapper = new GenericMapper<>(AnimalDTO.class);

        var animal = (AnimalDTO) genericMapper.map(person);

        assertEquals(person.getName(), animal.getName());
        assertEquals(person.getAge(), animal.getAge());
        assertTrue(animal.isAnimal());

    }

    public void testErrorWithTypeDifferent() {
        var person = new PersonDTO();
        try {
            person.accept(AnimalDTO.class);
        } catch (Exception e) {
            assertEquals("Class is not PersonDTO", e.getMessage());
        }
    }

    public void testErrorWithNullClass() {
        var person = new PersonDTO();
        try {
            person.accept(null);
        } catch (Exception e) {
            assertEquals("Class is not PersonDTO", e.getMessage());
        }
    }

    public void testErrorAnimalWithTypeDifferent() {
        var animal = new AnimalDTO();
        try {
            animal.accept(PersonDTO.class);
        } catch (Exception e) {
            assertEquals("Class is not AnimalDTO", e.getMessage());
        }
    }

    public void testErrorConvertWithNullClass() {
        var person = new PersonDTO();
        try {
            GenericMapper.convert(person, null);
        } catch (Exception e) {
            assertEquals("Erro ao mapear records", e.getMessage());
        }
    }

    public void testErrorConvertWithNullSource() {
        try {
            GenericMapper.convert(null, PersonDTO.class);
        } catch (MapperNotConvertErrorException e) {
            assertEquals("Erro ao mapear records", e.getMessage());
        }
    }

    public void testErrorConvertWithDataIncorrect() {
        var person = new PersonDTO();
        try {
            GenericMapper.convert(person, PersonDTO.class);
        } catch (MapperNotConvertErrorException e) {
            assertEquals("Erro ao mapear records", e.getMessage());
        }
    }
}