
function FormSegment() {
  return (
    <div className="bg-slace-200 p-7">
        <form action="">
            <h1 className="font-bold">Agregar segmento</h1>
            <br />
            <input className="bg-slate-400 rounded-md p-2 mb-2 block" type="number" name="segment-number"/>
            <input className="bg-slate-400 rounded-md p-2 mb-2 block" type="number" name="length"/>
            <input className="bg-slate-400 rounded-md p-2 mb-2 block" type="text" name="nomenclature"/>
            <button>Guardar</button>
        </form>

    </div>
  )
}

export default FormSegment