import React from "react";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Form,
  FormGroup,
  Label,
  Input,
  FormText
} from "reactstrap";

class Popup extends React.Component {
  render() {
    const { open, toggle, handleInput, submitForm } = this.props;
    const closeBtn = (
      <button className="close" onClick={toggle}>
        &times;
      </button>
    );
    return (
      <Modal isOpen={open} toggle={toggle} className={this.props.className}>
        <ModalHeader toggle={toggle} close={closeBtn}>
          Add User
        </ModalHeader>
        <ModalBody>
          <Form>
            <FormGroup>
              <Label for="title">Title</Label>
              <Input
                type="text"
                maxLength="254"
                name="title"
                id="title"
                placeholder="add title"
                onChange={e => handleInput(e, "title")}
              />
            </FormGroup>
            <FormGroup>
              <Label for="detail">Detail</Label>
              <Input
                type="textarea"
                maxLength="254"
                name="body"
                id="body"
                onChange={e => handleInput(e, "body")}
                placeholder="add details"
              />
            </FormGroup>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={toggle}>
            Cancel
          </Button>{" "}
          <Button color="secondary" onClick={()=> submitForm(toggle)}>
            Save
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

export default Popup;
