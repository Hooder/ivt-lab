package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init(){
    this.primary = mock(TorpedoStore.class);
    this.secondary = mock(TorpedoStore.class);
    this.ship = new GT4500(this.primary, this.secondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(primary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.fire(primary.getTorpedoCount())).thenReturn(true);
    when(secondary.fire(secondary.getTorpedoCount())).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primary, times(2)).fire(primary.getTorpedoCount());
  }

  @Test
  public void fireTorpedo_Single_Primary_Fired_Last_Success(){
    // Arrange
    when(secondary.fire(1)).thenReturn(true);
    when(primary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }
  
  @Test
  public void fireTorpedo_Single_Secondary_Fired_Last_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_Three_Shots_Success(){ // although primary was fired last time, but the secondary is empty
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.fire(0)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }
}
