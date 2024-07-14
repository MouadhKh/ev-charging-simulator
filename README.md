# EV Charging Station Simulator

This project simulates the behavior of electric vehicle (EV) charging stations over a year, providing insights into energy consumption, power demand, and usage patterns.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 17
- Maven

### Installation

1. Clone the repository:
```bash
   git clone https://github.com/MouadhKh/ev-charging-simulator.git
```
2. Navigate to the project directory:
```
cd ev-charging-simulator
```
3. Build the project, run the tests and install dependency locally:

```
mvn clean install
```

4. You can run via the jar or just `main()` in `ChargingStationSimulator`
## Usage


The simulation will run for a full year (365 days) in 15-minute intervals, simulating 20 charging points with 11kW power each. After completion, it will output:

- Total energy consumed (kWh)
- Theoretical maximum power demand
- Actual maximum power demand
- Concurrency factor