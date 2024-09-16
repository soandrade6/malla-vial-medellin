import FormSegment from "./components/FormSegment";
import ListSegment from "./components/ListSegment";

function HomePage() {
  return (
    <div>
      <h1>Malla Vial Medellin</h1>
        <div className="flex gap-x-10">
        <FormSegment></FormSegment>
        <ListSegment></ListSegment>
        </div>
      
    </div>
  );
}

export default HomePage;
