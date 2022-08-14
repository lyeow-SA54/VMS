import React, { useState } from "react";
import "../App.css";

export default function Image(props) {

    const [isOpen, setIsOpen] = useState(false);

    let handleShowDialog = () => () => {
        setIsOpen(!isOpen);
    }

    return (
        <div>
            <img height={100} width={100} src= "img/365d030e9a6d452aaef4c279b3918dda.jpeg" onMouseOver = {handleShowDialog()} onMouseOut={handleShowDialog()} alt="missing"/>
            {isOpen && (
          <dialog className="dialog" style={{ position: "absolute" }} open onClick={handleShowDialog()}>
            <img className="image" src="img/365d030e9a6d452aaef4c279b3918dda.jpeg" alt="missing"/>
          </dialog>
        )}
        </div>
    );

}